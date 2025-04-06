package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseMessage;

public interface EventResponseMessage extends ResponseMessage{
    String REGIST_FAIL = "regist fail";
    String SUBMIT_FAIL = "submit failed";
    String REDIRECT_FAIL = "redirect failed";
    String UPDATE_FAIL = "update failed";
}
