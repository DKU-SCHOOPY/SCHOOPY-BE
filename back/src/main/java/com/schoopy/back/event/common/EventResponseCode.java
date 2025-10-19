package com.schoopy.back.event.common;

import com.schoopy.back.global.common.ResponseCode;

public interface EventResponseCode extends ResponseCode{
    String REGIST_FAIL = "RGF";
    String SUBMIT_FAIL = "SF";
    String REDIRECT_FAIL = "RDF";
    String UPDATE_FAIL = "UF";
    String NOT_FOUND = "NF";
    String GET_FAIL = "FG";
    String DUPLICATION = "DP";
    String BAD_IMAGES = "BI";
    String DELETE_FAIL = "DF";
}
