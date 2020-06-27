package com.kakaopay.api.helper.exception;

/**
 * Request Header Validation Failed Exception
 */
public class MalformedRequestException extends Exception {
    public MalformedRequestException() {
        super("잘못된 형식의 요청입니다.");
    }
}
