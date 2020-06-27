package com.kakaopay.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

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
    @JsonIgnore
    private Long id;

    private Integer divMoney;

    @ColumnDefault("false")
    private Boolean isAcquire;

    private String acquiredUserId;

    @ManyToOne
    @JoinColumn(name = "token")
    @JsonIgnore
    private SprinkleVO sprinkle;
}
