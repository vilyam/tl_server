package com.c17.yyh.core.social;

import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c17.yyh.config.ServerConfig;
import com.c17.yyh.core.social.type.ErrorCode;
import com.c17.yyh.core.social.type.PaymentCheckAppKey;
import com.c17.yyh.core.social.type.SocialNetwork;

@Aspect
@Component
public class PaymentAspect {
    private static Logger logger = LoggerFactory.getLogger(PaymentAspect.class);

    @Autowired
    private ServerConfig serverConfig;

    @PostConstruct
    protected void initialize() {
        logger.debug("Initialized");
    }

    @Before("@annotation(com.c17.yyh.core.social.type.PaymentCheckAppKey) ")
    public void checkAppKey (JoinPoint joinPoint) throws PaymentException, NoSuchMethodException, SecurityException{
        String methodName = joinPoint.getSignature().getName();
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method method = clazz.getDeclaredMethod(methodName, HttpServletRequest.class);
        PaymentCheckAppKey ann = method.getAnnotation(PaymentCheckAppKey.class);

        HttpServletRequest req =  (HttpServletRequest) joinPoint.getArgs()[0];

        if (ann.sn() == SocialNetwork.MM || ann.sn() == SocialNetwork.VK) {
            String app_id = req.getParameter("app_id");

            if(app_id == null || Integer.parseInt(app_id) != serverConfig.socialAppId) {
               throw new PaymentException(ErrorCode.APP_ID_FAIL, 0 , "Not correct App key", req.getParameter("uid"));
            }
        }

        if (ann.sn() == SocialNetwork.OK) {
            String application_key = req.getParameter("application_key");

            if(application_key == null || !application_key.equalsIgnoreCase(serverConfig.socialPublicKey)) {
               throw new PaymentException(ErrorCode.APP_ID_FAIL, 0 , "Not correct App key",  req.getParameter("uid"));
            }
        }
    }

    @SuppressWarnings("unchecked")
	@Before("@annotation(com.c17.yyh.core.social.type.PaymentCheckMD5)")
    public void checkMD5 (JoinPoint joinPoint) throws PaymentException, NoSuchMethodException, SecurityException{
        HttpServletRequest req =  (HttpServletRequest) joinPoint.getArgs()[0];

        String sig = req.getParameter("sig");

        Map<String,String[]> params = new HashMap<String, String[]>(req.getParameterMap());
        List<String> listToSort= new LinkedList<String> ();
        String strToHash = "";
        params.remove("sig");

        for (String param : params.keySet()) {
            String value = req.getParameter(param);
            listToSort.add(param + "=" + value);
        }

        String[] sorted = listToSort.toArray(new String[0]);
        Arrays.sort(sorted, String.CASE_INSENSITIVE_ORDER);

        for (int i = 0; i < sorted.length; i++) {
            strToHash += sorted[i];
        }

        if(!checkMD5(strToHash, sig, serverConfig.socialSecretKey)) {
            throw new PaymentException(ErrorCode.MD5_FAIL, 1, "MD5 check fail", req.getParameter("uid"));
        }
    }

    public static boolean checkMD5(String params, String sig, String secretKey) {
        MessageDigest MD5 = null;
        String hash = null;
        try {
            MD5 = MessageDigest.getInstance("MD5");
            MD5.reset();
            MD5.update((params + secretKey).getBytes());
            byte[] array = MD5.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException | IllegalArgumentException e) {
            logger.error(e.getMessage());
        }
        return sig.equals(hash);
    }
}
