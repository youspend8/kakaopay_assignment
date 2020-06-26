package com.kakaopay.api.repository;

import com.kakaopay.api.entity.SprinkleDetailVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprinkleDetailRepository extends JpaRepository<SprinkleDetailVO, Long> {
    public SprinkleDetailVO findByTokenAndAcquiredUserId(String token, String userId);
}
