package com.kakaopay.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kakaopay.api.helper.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("뿌리기 조회 테스트")
public class SprinkleInfoTest extends AbstractSprinkleControllerTest {
    private static String token;
    private static String xRoomId = "294583";
    private static String xUserId = "999";

    @BeforeEach
    void beforeInfo() throws Exception {
        JsonNode resultJson = this.generateSprinkle(xRoomId, xUserId, 1000, 2);

        token = resultJson.get("data").asText();
    }

    @Test
    @DisplayName("조회")
    void infoTest() throws Exception {
        JsonNode resultJson = super.infoSprinkle(xRoomId, xUserId, token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_0.getCode());
    }

    @Test
    @DisplayName("내가 뿌린건이 아닌 경우")
    void infoTestNotSelf() throws Exception {
        JsonNode resultJson = super.infoSprinkle(xRoomId, "1024", token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_25.getCode());
    }

    @Test
    @DisplayName("유효하지 않은 토큰값일 경우")
    void infoTestInvalidToken() throws Exception {
        JsonNode resultJson = super.infoSprinkle(xRoomId, "1024", "AAA");

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_10.getCode());
    }
}
