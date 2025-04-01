package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseMessage;

public interface EventResponseMessage extends ResponseMessage{
    String REGIST_FAIL = "regist fail";
    String REMIT_FAIL = "remit failed";
    String SUBMIT_FAIL = "submit failed";
}
