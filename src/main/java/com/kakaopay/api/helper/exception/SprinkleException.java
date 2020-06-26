package com.kakaopay.api.helper.exception;

import com.kakaopay.api.helper.response.ResponseCode;
import lombok.Getter;

@Getter
public class SprinkleException extends Throwable {
    private int code;
    private String message;
    private ResponseCode responseCode;

    public SprinkleException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
        this.code = responseCode.getCode();
        this.message = responseCode.getMessage();
    }
}
