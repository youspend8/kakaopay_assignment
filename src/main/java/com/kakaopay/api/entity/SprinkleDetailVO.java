package com.kakaopay.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "sprinkle")
public class SprinkleDetailVO extends BaseVO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Integer divMoney;

    private Boolean isAcquire;

    private String acquiredUserId;

    @ManyToOne
    @JoinColumn(name = "token", insertable = false, updatable = false)
    @JsonIgnore
    private SprinkleVO sprinkle;
}
