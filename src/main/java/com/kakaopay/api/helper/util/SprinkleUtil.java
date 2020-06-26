package com.kakaopay.api.helper.util;

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
}
