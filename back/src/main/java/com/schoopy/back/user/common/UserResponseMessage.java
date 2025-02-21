package com.schoopy.back.user.common;

import com.schoopy.back.global.common.ResponseMessage;

public interface UserResponseMessage extends ResponseMessage{
    String MAIL_FAIL = "Mail send failed";
    String USER_NOT_EXISTS = "user is not exists";
    String DUPLICATE_EMAIL = "duplicated email";
    String DUPLICATE_ID = "duplicate id";
    String ENTITY_NOT_FOND = "entity not found";
    String CERTIFICATION_FAIL = "certification failed";
    String DUPLICATE_NICKNAME = "duplicated nickname";
    String EMAIL_MISSMATCH = "email missmatch";
    String PASSWORD_MISSMATCH = "password missmatch";
}
