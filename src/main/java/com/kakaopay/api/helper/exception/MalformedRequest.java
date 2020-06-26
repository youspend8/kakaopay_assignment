package com.kakaopay.api.helper.exception;

public class MalformedRequest extends Exception {
    public MalformedRequest() {
        super("잘못된 형식의 요청입니다.");
    }

    public MalformedRequest(String message) {
        super(message);

    }
}
