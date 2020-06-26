package com.kakaopay.api.service;

import com.kakaopay.api.controller.AbstractSprinkleControllerTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SprinkleServiceTest extends AbstractSprinkleControllerTest {
    private int money = 1000;
    private int division = 5;

    @Autowired
    private SprinkleService sprinkleService;

    @Test
    @DisplayName("뿌리기 생성 테스트")
    void generate() {
        assertDoesNotThrow(() -> sprinkleService.generate(money, division));
    }
}
