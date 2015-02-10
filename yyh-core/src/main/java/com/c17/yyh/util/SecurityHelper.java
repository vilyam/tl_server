package com.c17.yyh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecurityHelper {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityHelper.class);
    
    public static boolean checkMD5(Object message, String sig, String secretKey) {
        MessageDigest MD5 = null;
        String hash = null;
        try {
            MD5 = MessageDigest.getInstance("MD5");
            MD5.reset();
            MD5.update((HashHelper.messagePrepareToHash(message) + secretKey).getBytes());
            byte[] array = MD5.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < array.length; ++i) {
                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1, 3));
            }
            hash = sb.toString();
        } catch (NoSuchAlgorithmException | IllegalArgumentException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return sig.equals(hash);
    }
    
}
