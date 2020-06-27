package com.kakaopay.api.helper.util;

import com.kakaopay.api.entity.SprinkleVO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SprinkleUtil {
    /**
     * 뿌리기 생성 요청을 받아 금액을 나누는 Util Method
     * @param money - 뿌리기 생성시 Parameter money
     * @param division - 뿌리기 생성시 Parameter division
     * @return Integer List - 금액 리스트
     */
    public static List<Integer> divideMoney(int money, int division) {
        List<Integer> moneyList = new ArrayList<>();

        int remain = money;

        for (int i = 1; i < division; i++) {
            int max = ((remain - 1) - (division - i));
            int div = (int)(Math.random() * max + 1);
            remain -= div;
            moneyList.add(div);
        }

        moneyList.add(remain);
        return moneyList;
    }

    /**
     * 일 (Day) 기준 뿌리기 발생 이후 지난 날짜
     * @param sprinkleVO - VO
     * @param day - 기준일
     * @return boolean - 유효기간 만료 여부
     */
    public static boolean isExpireByDay(SprinkleVO sprinkleVO, int day) {
        return getInterval(sprinkleVO) >= (60 * 60 * 24 * day);
    }

    /**
     * 분 (Minute) 기준 뿌리기 발생 이후 지난 시간
     * @param sprinkleVO - VO
     * @param minute - 기준 시간 (분)
     * @return boolean - 유효기간 만료 여부
     */
    public static boolean isExpireByMinutes(SprinkleVO sprinkleVO, int minute) {
        return getInterval(sprinkleVO) >= (60 * minute);
    }

    private static long getInterval(SprinkleVO sprinkleVO) {
        return Duration.between(
                LocalDateTime.ofInstant(sprinkleVO.getCreateDate().toInstant(), ZoneId.systemDefault()),
                LocalDateTime.now())
                        .getSeconds();
    }
}
