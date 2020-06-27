package com.kakaopay.api.helper.util;

import com.kakaopay.api.entity.SprinkleVO;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class SprinkleUtil {
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

    public static boolean isExpireByDay(SprinkleVO sprinkleVO, int day) {
        return getInterval(sprinkleVO) >= (60 * 60 * 24 * day);
    }

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
