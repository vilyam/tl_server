package com.c17.yyh.mvc;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.social.ErrorCodesPrc;
import com.c17.yyh.core.social.PaymentException;
import com.c17.yyh.exceptions.ServerException;

import java.util.HashMap;
import java.util.Map;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.c17.yyh.server.message.outbound.ErrorMessage;

@ControllerAdvice
public class GlobalExceptionController {
    @Autowired
    private ServerConfig serverConfig;
    @Autowired
    ErrorCodesPrc erCodesPrc;

	private static Logger logger = LoggerFactory.getLogger("Payments");

	@ExceptionHandler(ServerException.class)
	public @ResponseBody Map<String, Object> handleServerException(ServerException ex) {
 
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("error", new ErrorMessage(ex.getErrorCode(), ex.getError_msg(), ex.getCritical()));

        return map;
	}
	
    @ExceptionHandler(PaymentException.class)
    public @ResponseBody HttpEntity<byte[]> handlePaymentException(PaymentException ex) throws JSONException {
        byte[] documentBody = null;
        HttpHeaders header = null;
        int error_code = erCodesPrc.getErrorCode(serverConfig.socialNetwork, ex.getCode());

        switch (serverConfig.socialNetwork) {
            case MM:

                documentBody = new JSONObject().put("status", ex.getStatus()).put("error_code", error_code).toString().getBytes();

                header = new HttpHeaders();
                header.setContentType(new MediaType("application", "json"));
                header.setContentLength(documentBody.length);
            break;
            case OK:
                StringBuilder xml = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
                xml.append("<ns2:error_response xmlns:ns2='http://api.forticom.com/1.0/'>");
                xml.append("<error_code>");
                xml.append(error_code);
                xml.append("</error_code>");
                xml.append("<error_msg>");
                xml.append(ex.getMessage());
                xml.append("</error_msg>");
                xml.append("</ns2:error_response>");

                documentBody = xml.toString().getBytes();

                header = new HttpHeaders();
                header.setContentType(new MediaType("application", "xml"));
                header.set("invocation-error", String.valueOf(error_code));
                header.setContentLength(documentBody.length);
            break;
            case VK:
                documentBody = new JSONObject().put("error", new JSONObject()
                .put("error_code", error_code)
                .put("error_msg", ex.getMessage())
                .put("critical", ex.getStatus()))
                .toString().getBytes();

                header = new HttpHeaders();
                header.setContentType(new MediaType("application", "json"));
                header.setContentLength(documentBody.length);
            break;
            case FB:
                break;
            default:
                break;
        }

        logger.error("uid: {}, message: {}", ex.getUid(), ex.getMessage());
        return new HttpEntity<>(documentBody, header);
    }
}
