package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseMessage;

public interface EventResponseMessage extends ResponseMessage{
    String REGIST_FAIL = "regist fail";
    String SUBMIT_FAIL = "submit failed";
    String DUPLICATION = "duplicated application";
    String NOT_FOUND = "not found";
    String REDIRECT_FAIL = "redirect failed";
    String UPDATE_FAIL = "update failed";
    String GET_FAIL = "get failed";
    String BAD_IMAGES = "bad images request";
}