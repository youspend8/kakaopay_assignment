package com.kakaopay.api.controller;

import com.kakaopay.api.helper.response.ResponseMessage;
import com.kakaopay.api.helper.response.ResponseCode;

public abstract class BaseController {

    protected void failure(
            ResponseMessage.ResponseMessageBuilder<?> builder, ResponseCode responseCode) {
        builder.code(responseCode.getCode())
               .message(responseCode.getMessage());
    }
}
