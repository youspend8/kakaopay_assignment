package com.kakaopay.api.repository;

import com.kakaopay.api.entity.SprinkleVO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SprinkleRepository extends JpaRepository<SprinkleVO, String> {
    @Query("SELECT S FROM SprinkleVO S INNER JOIN FETCH S.sprinkleDetail WHERE S.token = :token")
    public Optional<SprinkleVO> findById(
            @Param("token") String token);
}
