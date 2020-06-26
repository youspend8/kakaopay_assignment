package com.kakaopay.api.repository;

import com.kakaopay.api.entity.SprinkleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprinkleRepository extends JpaRepository<SprinkleVO, String> {
    public SprinkleVO findByToken(String token);
}
