package com.c17.yyh.core.admin.type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface AdminCommandType {

    String name();

    GrantLevelType level() default GrantLevelType.ADMIN;
}
