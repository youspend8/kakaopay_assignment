package com.kakaopay.api.helper.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Repeat;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SprinkleUtilTest {

    private final int money = 200;
    private final int division = 5;
    private List<Integer> moneyList = new ArrayList<>();

    @BeforeEach
    void before() {
        moneyList = SprinkleUtil.divideMoney(money, division);
    }

    @RepeatedTest(1000)
    @DisplayName("뿌리기 금액 나누기 테스트 - 올바른 횟수로 나눔여부")
    void divideTestDivision() {
        assertEquals(division, moneyList.size());
    }

    @RepeatedTest(1000)
    @DisplayName("뿌리기 금액 나누기 테스트 - 0원으로 나누어지는 경우 존재여부")
    void divideTestZero() {
        System.out.println(moneyList);
        assertFalse(moneyList.contains(0));
    }

    @RepeatedTest(1000)
    @DisplayName("뿌리기 금액 나누 테스트 - 나눠진 금액들의 합이 Total Money 값과 동일여부")
    void divideTestTotal() {
        int sum = moneyList.stream()
                .mapToInt(x -> x)
                .sum();
        assertEquals(money, sum);
    }
}
