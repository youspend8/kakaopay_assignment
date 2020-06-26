package com.kakaopay.api.helper.exception;

public class MalformedRequestException extends Exception {
    public MalformedRequestException() {
        super("잘못된 형식의 요청입니다.");
    }
}
