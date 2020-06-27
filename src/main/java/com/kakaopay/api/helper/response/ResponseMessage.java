package com.kakaopay.api.helper.response;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseMessage<T> {
    @Builder.Default
    private int code = 0;
    private String message;
    private T data;
}
