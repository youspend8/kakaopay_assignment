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
        System.out.println("list :: " + list);
        return this.list.stream()
                .filter(x -> !x.getIsAcquire())
                .collect(Collectors.toList());
    }
}
