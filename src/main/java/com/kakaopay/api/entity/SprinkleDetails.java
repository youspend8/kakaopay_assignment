package com.kakaopay.api.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Embeddable;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@Embeddable
@NoArgsConstructor
@ToString
public class SprinkleDetails {

    @OneToMany(mappedBy = "sprinkle", cascade = CascadeType.ALL)
    private List<SprinkleDetailVO> list;

    public List<SprinkleDetailVO> filterNotAcquired() {
        return this.list.stream()
                .filter(x -> !x.getIsAcquire())
                .collect(Collectors.toList());
    }

    public List<SprinkleDetailVO> filterByUserId(String userId) {
        return this.list.stream()
                .filter(x -> x.getAcquiredUserId() != null)
                .filter(x -> x.getAcquiredUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public boolean isSoldOut() {
        long acquireCount = this.list.stream()
                .filter(SprinkleDetailVO::getIsAcquire)
                .count();
        return this.list.size() == acquireCount;
    }
}
