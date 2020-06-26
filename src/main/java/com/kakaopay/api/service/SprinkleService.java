package com.kakaopay.api.service;

import com.kakaopay.api.entity.GeneratedInfoVO;
import com.kakaopay.api.entity.SprinkleDetailVO;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.SprinkleException;
import com.kakaopay.api.helper.exception.MalformedRequestException;
import com.kakaopay.api.helper.response.ResponseCode;
import com.kakaopay.api.helper.util.SprinkleUtil;
import com.kakaopay.api.helper.util.TokenUtil;
import com.kakaopay.api.repository.SprinkleDetailRepository;
import com.kakaopay.api.repository.SprinkleRepository;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SprinkleService extends BaseService {
    private final SprinkleRepository sprinkleRepository;
    private final SprinkleDetailRepository sprinkleDetailRepository;

    public SprinkleVO generate(int money, int division) throws Exception {
        GeneratedInfoVO generatedInfoVO = GeneratedInfoVO.builder()
                .genRoomId(this.getRequestRoomId())
                .genUserId(this.getRequestUserId())
                .build();

        SprinkleVO sprinkleVO = SprinkleVO.builder()
                .token(this.generateToken())
                .money(money)
                .division(division)
                .createDate(new Date())
                .generatedInfo(generatedInfoVO)
                .build();

        sprinkleVO.setSprinkleDetail(this.generateDetail(sprinkleVO));

        log.info(">> Generate SprinkleVO :: " + sprinkleVO);

        sprinkleRepository.save(sprinkleVO);

        return sprinkleVO;
    }

    public SprinkleVO fetchInfo(String token) throws MalformedRequestException, SprinkleException {
        SprinkleVO sprinkleVO = sprinkleRepository.findById(token)
                .orElseThrow(() -> new SprinkleException(ResponseCode.CODE_10));

        String userId = this.getRequestUserId();

        if (userId.equals(sprinkleVO.getGeneratedInfo().getGenUserId())) {
            return sprinkleVO;
        }
        return null;
    }
    
    public SprinkleDetailVO acquire(String token) throws MalformedRequestException, SprinkleException {
        final String xUserId = this.getRequestUserId();
        final String xRoomId = this.getRequestRoomId();

        SprinkleVO sprinkleVO = sprinkleRepository.findById(token)
                .orElseThrow(() -> new SprinkleException(ResponseCode.CODE_10));

        GeneratedInfoVO generatedInfoVO = sprinkleVO.getGeneratedInfo();

        if (xUserId.equals(generatedInfoVO.getGenUserId())) {
            throw new SprinkleException(ResponseCode.CODE_20);
        }

        if (sprinkleDetailRepository.findByTokenAndAcquiredUserId(token, xUserId) != null) {
            throw new SprinkleException(ResponseCode.CODE_21);
        }

        LocalDateTime createDate = LocalDateTime.ofInstant(sprinkleVO.getCreateDate().toInstant(), ZoneId.systemDefault());

        long lapseSec = Duration.between(createDate, LocalDateTime.now()).getSeconds();

        System.out.println("lapseSec :: " + lapseSec);

        if (lapseSec >= 600) {
            throw new SprinkleException(ResponseCode.CODE_22);
        }

        if (!xRoomId.equals(generatedInfoVO.getGenRoomId())) {
            throw new SprinkleException(ResponseCode.CODE_23);
        }

        List<SprinkleDetailVO> sprinkleDetailList = sprinkleVO.getSprinkleDetail()
                .filterNotAcquired();

        Collections.shuffle(sprinkleDetailList);

        System.out.println("sprinkleDetailList :: " + sprinkleDetailList);
        return sprinkleDetailList.get(0);
    }

    /**
     * 뿌리기 금액을 인원수만큼 나눠 SprinkleDetail 타입으로 반환
     * @param money
     * @param division
     * @return
     */
    private List<SprinkleDetailVO> generateDetail(SprinkleVO sprinkleVO) throws Exception {
        final int money = sprinkleVO.getMoney();
        final int division = sprinkleVO.getDivision();

        if (money < division) {
            throw new Exception("뿌리기 금액은 최소 지정한 인원수 이상이어야 됩니다.");
        }

        List<Integer> moneyList = SprinkleUtil.divideMoney(money, division);

        return moneyList.stream()
                .map(x -> SprinkleDetailVO.builder()
                        .isAcquire(false)
                        .divMoney(x)
                        .build())
                .collect(Collectors.toList());
    }

    private String generateToken() {
        String token;
        do {
            token = TokenUtil.generate();
        } while (this.isDistinctToken(token));

        return token;
    }

    private boolean isDistinctToken(String token) {
        System.out.println("token :: " +token);
        System.out.println("sprinkleRepository.findByToken(token) :: " + sprinkleRepository.findById(token));
        return sprinkleRepository.findById(token).orElse(null) != null;
    }
}
