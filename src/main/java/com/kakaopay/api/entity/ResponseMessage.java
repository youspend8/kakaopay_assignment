package com.kakaopay.api.entity;

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
