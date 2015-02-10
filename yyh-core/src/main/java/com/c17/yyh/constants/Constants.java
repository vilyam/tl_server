package com.c17.yyh.constants;

public class Constants {

    public static final long MSEC_IN_SEC = 1000;
    public static final long MSEC_IN_MIN = 60 * MSEC_IN_SEC;
    public static final long MSEC_IN_HOUR = 60 * MSEC_IN_MIN;
    public static final long MSEC_IN_HALFDAY = 12 * MSEC_IN_HOUR;
    public static final long MSEC_IN_DAY = 2 * MSEC_IN_HALFDAY;

    public static final long STUCK_TIME = 3 * MSEC_IN_DAY;

    public static final String separator = System.getProperty("file.separator");

    public static final int STOCK_PAYMENT_MAGIC_NUMBER = 142536;
    public static final String DEFAULT_DATE_FORMAT = "dd-MM-yyyy HH:mm";

}
