package com.c17.yyh.core.social;

import org.springframework.stereotype.Service;

import com.c17.yyh.core.social.type.ErrorCode;
import com.c17.yyh.core.social.type.SocialNetwork;
import com.c17.yyh.server.ErrorCodes;

@Service
public class ErrorCodesPrc {
    public int getErrorCode(SocialNetwork sn, ErrorCode code) {
        switch (sn) {
            case MM:
                switch (code) {
                    case APP_ID_FAIL:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                    case DB_ERROR:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                    case GENERAL:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                    case ITEM_INCORRECT_PRICE:
                        return ErrorCodes.MM.MAIL_RU_INCORRECT_PRICE_FOR_USER;
                    case ITEM_NOT_FOUND:
                        return ErrorCodes.MM.MAIL_RU_SERVICE_NOT_FOUND;
                    case MD5_FAIL:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                    case OTHER:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                    case USER_NOT_FOUND:
                        return ErrorCodes.MM.MAIL_RU_USER_NOT_FOUND;
                    default:
                        return ErrorCodes.MM.MAIL_RU_OTHER;
                }
            case OK:
                switch (code) {
                    case APP_ID_FAIL:
                        return ErrorCodes.OK.CALLBACK_INVALID_PAYMENT;
                    case DB_ERROR:
                        return ErrorCodes.OK.SYSTEM;
                    case GENERAL:
                        return ErrorCodes.OK.UNKNOWN;
                    case ITEM_INCORRECT_PRICE:
                        return ErrorCodes.OK.CALLBACK_INVALID_PAYMENT;
                    case ITEM_NOT_FOUND:
                        return ErrorCodes.OK.CALLBACK_INVALID_PAYMENT;
                    case MD5_FAIL:
                        return ErrorCodes.OK.PARAM_SIGNATURE;
                    case OTHER:
                        return ErrorCodes.OK.UNKNOWN;
                    case USER_NOT_FOUND:
                        return ErrorCodes.OK.CALLBACK_INVALID_PAYMENT;
                    default:
                        return ErrorCodes.OK.UNKNOWN;
                }
            case VK:
                switch (code) {
                    case APP_ID_FAIL:
                        return ErrorCodes.VK.INTEGRITY_REQUEST_ERROR;
                    case DB_ERROR:
                        return ErrorCodes.VK.TEMP_DB_ERROR;
                    case GENERAL:
                        return ErrorCodes.VK.GENERAL_ERROR;
                    case ITEM_INCORRECT_PRICE:
                        return ErrorCodes.VK.INTEGRITY_REQUEST_ERROR;
                    case ITEM_NOT_FOUND:
                        return ErrorCodes.VK.PRODUCT_DOES_NOT_EXIST;
                    case MD5_FAIL:
                        return ErrorCodes.VK.MISMATCH_CALC_AND_TRANSMITTED_SIG;
                    case OTHER:
                        return ErrorCodes.VK.GENERAL_ERROR;
                    case USER_NOT_FOUND:
                        return ErrorCodes.VK.USER_DOES_NOT_EXIST;
                    default:
                        return ErrorCodes.VK.GENERAL_ERROR;
                }
            default:
                return 0;
        }
    }
}
