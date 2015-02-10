package com.c17.yyh.server;

public final class ErrorCodes {

    public static final int NO_ERROR = 0;
	
	//General application errors
	public static final int INCORRECT_COMMAND = 100;
	public static final int PARSE_ERROR = 101; 
	public static final int CONNECTED_TWICE = 102;
	public static final int INBOUND_READ_ERROR = 103;
	public static final int CREATE_MESSAGE_ERROR = 104;
	public static final int NO_OUTBOUND_MESSAGE = 105;
	public static final int CONFIG_ERROR = 106;
	
	public static final int SESSION_ALREADY_REGISTERED = 120;
	public static final int SESSION_NOT_REGISTERED = 121;
	public static final int TIMEOUT_SINCE_LAST_SESSION = 122;
	public static final int NOT_ENOUGH_SESSION = 123;
	public static final int USER_NOT_REGISTERED = 124;
	public static final int USER_ALREADY_REGISTERED = 125;
	public static final int USER_NOT_LOGGED_IN = 126;
	public static final int USER_GAME_SESSIONS_HAS_NOT_EQUALS_VALUES = 127; //HACK
	public static final int MISMATCH_CALC_AND_TRANSMITTED_SIG = 128;
	
	public static final int RESOURCE_NOT_FOUND = 200;
	public static final int NOT_ENOUGH_RESOURCES = 202;
	public static final int NOT_ENOUGH_MONEY = 203;
	public static final int INCORRECT_MONEY_TYPE = 204;
	public static final int INCORRECT_COLLECTION_ID = 205;
	public static final int NOT_ENOUGH_ADVENTURE_STARS = 206;
	public static final int INCORRECT_LELEL_ID = 207;

    //Tools error
    public static final int UNKNOWN_TOOL = 301;
    
	public static final int PET_NOT_EXISTS = 401; 
	public static final int TREASURE_NOT_EXISTS = 402; 
	public static final int BOSS_NOT_EXISTS = 403; 
	
    public static final int UNKNOWN_GIFT_ERROR = 300;
    public static final int UNVALID_GIFT_COUNT_ERROR = 301;
    public static final int UNKNOWN_SENDER_ERROR = 302;
    public static final int UNKNOWN_RECEIVER_ERROR = 303;
    public static final int ABSENT_GIFT_ERROR = 304;
    public static final int ABSENT_RECEIVER_ERROR = 305;
    public static final int UNKNOWN_PRESENT_ERROR = 306;
    public static final int UNKNOWN_ASK_PRESENT_ERROR = 307;
    public static final int UNVALID_GIFT_ID_ERROR = 308;

    //collections
    public static final int COLLECTION_WAS_TAKED = 500;
	public static final int COLLECTION_NOT_COLLECTED = 501;
	public static final int COLLECTION_IS_NULL = 502;

	//db
	public static final int TEMP_DB_ERROR = 600;

    public static final int STOCK_NOT_VALID = 700; 
    public static final int STOCK_PAYMENT_NOT_VALID = 701; 

    public static final class ADMIN{
        public static final int OPERATION_FORBIDDEN = 800;
        public static final int PARAMETERS_NOT_CORRECT = 801;
    }
    
    //odnoklassniki errors
    public static final class OK{
    	public static final int UNKNOWN = 1;						//Неизвестная ошибка
    	public static final int SERVICE = 2; 						//Сервис временно недоступен
    	public static final int CALLBACK_INVALID_PAYMENT = 1001; 	//Платеж неверный и не может быть обработан
    	public static final int SYSTEM = 9999; 						//Критический системный сбой, который невозможно устранить
    	public static final int PARAM_SIGNATURE = 104; 				//Неверная подпись 
    }
    
	//mail.ru
    public static final class MM{
        public static final int MAIL_RU_USER_NOT_FOUND = 701; 			//User not found — если приложение не смогло найти пользователя для оказания услуги
        public static final int MAIL_RU_SERVICE_NOT_FOUND = 702; 		//Service not found — если услуга с данный идентификатором не существуем в вашем приложении
        public static final int MAIL_RU_INCORRECT_PRICE_FOR_USER = 703; //Incorrect price for given uid and service_id — если данная услуга для данного пользователя не могла быть оказана за указанную цену
        public static final int MAIL_RU_OTHER = 700; 	
    }
    
  //VK errors
    public static final class VK{
    	public static final int GENERAL_ERROR = 1;
    	public static final int TEMP_DB_ERROR = 2;
    	public static final int MISMATCH_CALC_AND_TRANSMITTED_SIG = 10;
    	public static final int INTEGRITY_REQUEST_ERROR = 11;
    	public static final int PRODUCT_DOES_NOT_EXIST = 20;
    	public static final int PRODUCT_NO_LONGER_AVAILABLE = 21;
    	public static final int USER_DOES_NOT_EXIST = 22;
    }
}
