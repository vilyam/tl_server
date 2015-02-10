package com.c17.yyh.core.admin.type;



/**
 *
 * @author sigurd
 */
public interface GrantCallback {

    boolean isAccessAllowed(GrantLevelType level);
}
