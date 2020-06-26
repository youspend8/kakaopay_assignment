package com.kakaopay.api.helper.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResponseCode {
    CODE_0(0, "정상처리"),
    CODE_10(10, "유효하지 않은 토큰값니다."),
    CODE_11(11, "요청 헤더 정보가 부족합니다."),
    CODE_20(20, "자신이 뿌리기한 건은 자신이 받을 수 없습니다."),
    CODE_21(21, "뿌리기 당 한 사용자는 한번만 받을 수 있습니다."),
    CODE_22(22, "뿌린 건은 10분간만 유효합니다."),
    CODE_23(23, "뿌리기가 호출된 대화방과 동일한 대화방에 속한 사용자만이 받을 수 있습니다.");

    private int code;
    private String message;
}
