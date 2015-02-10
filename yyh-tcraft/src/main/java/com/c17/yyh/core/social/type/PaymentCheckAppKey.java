package com.c17.yyh.core.social.type;

import java.lang.annotation.*;

import org.springframework.stereotype.Component;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PaymentCheckAppKey {
    SocialNetwork sn();
}
