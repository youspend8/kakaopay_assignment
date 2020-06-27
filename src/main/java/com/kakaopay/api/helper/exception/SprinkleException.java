package com.kakaopay.api.helper.exception;

import com.kakaopay.api.helper.response.ResponseCode;
import lombok.Getter;

/**
 * 뿌리기 공통 처리 결과 Exception
 */
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
