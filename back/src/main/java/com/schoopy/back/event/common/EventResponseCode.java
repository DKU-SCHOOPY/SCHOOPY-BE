package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseCode;

public interface EventResponseCode extends ResponseCode{
    String REGIST_FAIL = "RGF";
    String REMIT_FAIL = "RMF";
    String SUBMIT_FAIL = "SF";
}
