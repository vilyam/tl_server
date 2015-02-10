package com.c17.yyh.gateway;

import com.c17.yyh.config.ServerConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.AsyncRestTemplate;

/**
 *
 * @author sigurd
 */
@Component
public class Notifier {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ServerConfig serverConfig;

    private final ObjectMapper mapper = new ObjectMapper();

    public void notify(String[] uids, Date date, String message, final AsyncResult<String> callback) {
        try {
            String url = "http://" + ServerConfig.getNotificationServerAddress() + "/yyh-notification-server/sendMessage";
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            Map<String, Object> jsonBody = new HashMap<>();
            jsonBody.put("socialNetwork", serverConfig.socialNetwork.getName());
            jsonBody.put("uids", StringUtils.join(uids, ","));
            jsonBody.put("message", message);
            jsonBody.put("date", date);
            ListenableFuture<ResponseEntity<String>> futureEntity = new AsyncRestTemplate()
                    .exchange(url, HttpMethod.POST, new HttpEntity(mapper.writeValueAsString(jsonBody),
                                    headers), String.class);
            futureEntity
                    .addCallback(new ListenableFutureCallback<ResponseEntity<String>>() {
                        @Override
                        public void onSuccess(ResponseEntity<String> result) {
                            callback.onResult(result.getBody());
                        }

                        @Override
                        public void onFailure(Throwable t) {
                            LoggerFactory.getLogger(Notifier.class).error(t.getMessage());
                        }
                    });
        } catch (JsonProcessingException ex) {
            logger.error(ex.getMessage());
        }
    }

    public abstract class AsyncResult<T> {

        public abstract void onResult(T result);

        public void onFailure(Throwable t) {
        }
    }

}
