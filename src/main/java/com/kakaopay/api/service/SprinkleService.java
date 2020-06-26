package com.kakaopay.api.service;

import com.kakaopay.api.entity.GeneratedInfoVO;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.MalformedRequest;
import com.kakaopay.api.helper.util.TokenUtil;
import com.kakaopay.api.repository.SprinkleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SprinkleService extends BaseService {
    private final SprinkleRepository sprinkleRepository;

    public SprinkleVO generateSprinkle(int money, int division) throws MalformedRequest {
        GeneratedInfoVO generatedInfoVO = GeneratedInfoVO.builder()
                .genRoomId(this.getRequestRoomId())
                .genUserId(this.getRequestUserId())
                .build();

        return SprinkleVO.builder()
                .token(this.generateToken())
                .money(money)
                .division(division)
                .generatedInfo(generatedInfoVO)
                .build();
    }

    private String generateToken() {
        String token;
        do {
            token = TokenUtil.generate();
        } while (this.isDistinctToken(token));

        return token;
    }

    private boolean isDistinctToken(String token) {
        return sprinkleRepository.findByToken(token) != null;
    }
}
