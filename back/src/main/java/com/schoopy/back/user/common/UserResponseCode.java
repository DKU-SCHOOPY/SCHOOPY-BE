package com.schoopy.back.user.common;

import com.schoopy.back.global.common.ResponseCode;

public interface UserResponseCode extends ResponseCode {
    String MAIL_FAIL = "MF";
    String USER_NOT_EXISTS = "UNE";
    String ENTITY_NOT_FOND = "ENF";
    String CERTIFICATION_FAIL = "CF";
    String DUPLICATE_EMAIL = "DE";
    String DUPLICATE_ID = "DI";
    String DUPLICATE_NICKNAME = "DN";
    String EMAIL_MISSMATCH = "EM";
    String PASSWORD_MISSMATCH = "PM";
}
