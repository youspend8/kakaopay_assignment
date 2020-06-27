package com.kakaopay.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kakaopay.api.helper.response.ResponseCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("뿌리기 생성")
public class SprinkleGenerateTest extends AbstractSprinkleControllerTest {
    private static String xRoomId = "294583";
    private static String xUserId = "999";

    @RepeatedTest(100)
    @DisplayName("100회 반복")
    void generateTest() throws Exception {
        JsonNode resultJson = generateSprinkle(xRoomId, xUserId, 1000, 2);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_0.getCode());
    }
}
