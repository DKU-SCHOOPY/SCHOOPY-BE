package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseCode;

public interface EventResponseCode extends ResponseCode{
    String REGIST_FAIL = "RGF";
    String SUBMIT_FAIL = "SF";
    String REDIRECT_FAIL = "RDF";
    String UPDATE_FAIL = "UF";
}
