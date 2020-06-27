package com.kakaopay.api.service;

import com.kakaopay.api.entity.GeneratedInfoVO;
import com.kakaopay.api.entity.SprinkleDetailVO;
import com.kakaopay.api.entity.SprinkleDetails;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.exception.MalformedRequestException;
import com.kakaopay.api.helper.exception.SprinkleException;
import com.kakaopay.api.helper.response.ResponseCode;
import com.kakaopay.api.helper.util.SprinkleUtil;
import com.kakaopay.api.helper.util.TokenUtil;
import com.kakaopay.api.repository.SprinkleDetailRepository;
import com.kakaopay.api.repository.SprinkleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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

    public SprinkleVO generate(int money, int division) throws MalformedRequestException, SprinkleException {
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

        final String userId = this.getRequestUserId();
        final String genUserId = sprinkleVO.getGeneratedInfo().getGenUserId();

        if (!userId.equals(genUserId))
            throw new SprinkleException(ResponseCode.CODE_25);
        if (SprinkleUtil.isExpireByDay(sprinkleVO, 7))
            throw new SprinkleException(ResponseCode.CODE_26);

        return sprinkleVO;
    }
    
    public SprinkleDetailVO acquire(String token) throws MalformedRequestException, SprinkleException {
        final String xUserId = this.getRequestUserId();
        final String xRoomId = this.getRequestRoomId();

        SprinkleVO sprinkleVO = sprinkleRepository.findById(token)
                .orElseThrow(() -> new SprinkleException(ResponseCode.CODE_10));
        GeneratedInfoVO generatedInfoVO = sprinkleVO.getGeneratedInfo();
        SprinkleDetails sprinkleDetails = sprinkleVO.getSprinkleDetail();

        System.out.println("sprinkleVO :: " + sprinkleVO);
        if (!xRoomId.equals(generatedInfoVO.getGenRoomId()))
            throw new SprinkleException(ResponseCode.CODE_23);
        if (xUserId.equals(generatedInfoVO.getGenUserId()))
            throw new SprinkleException(ResponseCode.CODE_20);
        if (SprinkleUtil.isExpireByMinutes(sprinkleVO, 10))
            throw new SprinkleException(ResponseCode.CODE_22);
        if (sprinkleDetails.isSoldOut())
            throw new SprinkleException(ResponseCode.CODE_24);
        if (sprinkleDetails.filterByUserId(xUserId).size() != 0)
            throw new SprinkleException(ResponseCode.CODE_21);

        List<SprinkleDetailVO> sprinkleDetailList = sprinkleVO.getSprinkleDetail()
                .filterNotAcquired();

        Collections.shuffle(sprinkleDetailList);

        SprinkleDetailVO sprinkleDetailVO = sprinkleDetailList.get(0);
        sprinkleDetailVO.setAcquiredUserId(xUserId);
        sprinkleDetailVO.setIsAcquire(true);

        return sprinkleDetailRepository.save(sprinkleDetailVO);
    }

    /**
     * 뿌리기 금액을 인원수만큼 나눠 SprinkleDetail 타입으로 반환
     * @param money
     * @param division
     * @return List<SprinkleDetailVO> - 뿌리기 상세 리스트
     */
    private List<SprinkleDetailVO> generateDetail(SprinkleVO sprinkleVO) throws SprinkleException {
        final int money = sprinkleVO.getMoney();
        final int division = sprinkleVO.getDivision();

        if (money < division) {
            throw new SprinkleException(ResponseCode.CODE_30);
        }

        List<Integer> moneyList = SprinkleUtil.divideMoney(money, division);

        return moneyList.stream()
                .map(x -> SprinkleDetailVO.builder()
                        .isAcquire(false)
                        .divMoney(x)
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 토큰 값 중복 체크 및 발급
     * @return token
     */
    private String generateToken() {
        String token;
        do {
            token = TokenUtil.generate();
        } while (this.isDistinctToken(token));

        return token;
    }

    private boolean isDistinctToken(String token) {
        return sprinkleRepository.findById(token).orElse(null) != null;
    }
}
