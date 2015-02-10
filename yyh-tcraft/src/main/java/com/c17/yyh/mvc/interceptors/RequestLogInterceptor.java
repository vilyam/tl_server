package com.c17.yyh.mvc.interceptors;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestLogInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(RequestLogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        logger.info("----New!----");
        logger.info("Method: {}", method);
        logger.info("Content-type: {}", request.getContentType());
        logger.info("Query parameters: {}", request.getQueryString());

        if ("POST".equalsIgnoreCase(method)) {
            StringBuilder sb = new StringBuilder();
            Enumeration<String> parameterNames = request.getParameterNames();
            while (parameterNames.hasMoreElements()) {

                String paramName = parameterNames.nextElement();
                String[] paramValues = request.getParameterValues(paramName);

                for (int i = 0; i < paramValues.length; i++) {
                    sb.append(paramName);
                    sb.append("=");
                    sb.append(paramValues[i]);
                    sb.append("&");
                }

            }
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }

            logger.info("POST body: {}", sb.toString());
        }

        return true;
    }
}
