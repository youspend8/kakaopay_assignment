package com.kakaopay.api.helper.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TokenUtilTest {

    @Test
    @DisplayName(value="Token 중복 여부 테스트")
    void generate() {
//        final int COUNT = 100000;
//
//        List<String> tokenList = IntStream.range(0, COUNT)
//                .mapToObj(x -> TokenUtil.generate())
//                .distinct()
//                .collect(Collectors.toList());
//
//        assertEquals(tokenList.size(), COUNT);
    }
}