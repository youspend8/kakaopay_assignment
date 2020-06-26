package com.kakaopay.api.helper.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class TokenUtil {
    private static final Random random = new Random();
    private static final String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String generate() {
        return IntStream.range(0, 3)
                .mapToObj(x -> String.valueOf(getChar()))
                .collect(Collectors.joining());
    }

    private static char getChar() {
        return str.charAt(random.nextInt(str.length() - 1));
    }
}
