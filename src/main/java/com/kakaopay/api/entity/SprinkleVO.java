package com.kakaopay.api.entity;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SprinkleVO extends BaseVO {
    @Id
    private String token;
    private Integer money;
    private Integer division;

    @Embedded
    private GeneratedInfoVO generatedInfo;
}
