package com.c17.yyh.core;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.c17.yyh.config.ServerConfig;

@Component("scrambler")
public class Scrambler {
    private final int KEY_SIZE = 5;

    @Autowired
    private ServerConfig serverConfig;

    public String encode(String msg) {
        if (!serverConfig.encriptionEnabled) {
            return msg;
        }

        byte[] byteMsg = null;
        try {
            byteMsg = msg.getBytes("UTF8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        int third = byteMsg.length / 3;

        byte[] key = new byte[KEY_SIZE];
        for (int i = 0; i < KEY_SIZE; ++i ) {
            key[i] = byteMsg[third + i];
        }

        byte[] res = new byte[byteMsg.length + KEY_SIZE];
        for (int i = 0; i < third; ++i) {
            res[i] = (byte) (byteMsg[i] ^ key[i % key.length]);
        }

        for (int i = 0; i < KEY_SIZE; ++i) {
            res[third+i] = key[i];
        }

        for (int i = third + KEY_SIZE; i < byteMsg.length + KEY_SIZE; ++i) {
            res[i] = (byte) (byteMsg[i - KEY_SIZE] ^ key[i % KEY_SIZE]);
        }

        return new BASE64Encoder().encode(res);
    }

    public String decode(String msg) {
        if (!serverConfig.encriptionEnabled) {
            return msg;
        }
        byte[] byteMsg = null;
        try {
            byteMsg = new BASE64Decoder().decodeBuffer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int third = (byteMsg.length-KEY_SIZE)/3;
        byte[] key = new byte[KEY_SIZE];
        for (int i = 0; i < KEY_SIZE; ++i) {
            key[i] = byteMsg[third + i];
        }

        byte[] res = new byte[byteMsg.length-KEY_SIZE];

        for (int i = 0; i < third; ++i) {
            res[i] = (byte) (byteMsg[i] ^ key[i % KEY_SIZE]);
        }

        for (int i = third+KEY_SIZE; i < byteMsg.length; ++i) {
            res[i - KEY_SIZE] = (byte) (byteMsg[i] ^ key[i % KEY_SIZE]);
        }

        try { 
            return StringEscapeUtils.unescapeJava( new String(res, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }
}
