package com.kakaopay.api.entity;

import lombok.*;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.List;

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
    private Date createDate;

    @Embedded
    private GeneratedInfoVO generatedInfo;

    @Embedded
    private SprinkleDetails sprinkleDetail;

    public void setSprinkleDetail(List<SprinkleDetailVO> sprinkleDetail) {
        this.sprinkleDetail = new SprinkleDetails(sprinkleDetail);
        for (SprinkleDetailVO sprinkleDetailVO : sprinkleDetail) {
            sprinkleDetailVO.setSprinkle(this);
        }
    }
}
