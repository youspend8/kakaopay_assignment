package com.kakaopay.api.entity;

import lombok.*;

import javax.persistence.Embeddable;

@Embeddable
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GeneratedInfoVO {
    private String genUserId;
    private String genRoomId;
}
