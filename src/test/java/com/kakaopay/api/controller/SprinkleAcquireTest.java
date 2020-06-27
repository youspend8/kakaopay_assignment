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
@DisplayName("뿌리기 줍기 테스트")
public class SprinkleAcquireTest extends AbstractSprinkleControllerTest {
    private static String token;
    private static String xRoomId = "294583";
    private static String xUserId = "999";

    @BeforeEach
    void beforeAcquire() throws Exception {
        JsonNode resultJson = this.generateSprinkle(xRoomId, xUserId, 1000, 2);

        token = resultJson.get("data").asText();
    }

    @Test
    @DisplayName("자신이 뿌린건이 아닐경우")
    void acquireTest() throws Exception {
        JsonNode resultJson = acquireSprinkle(xRoomId, "1024", token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_0.getCode());
    }

    @Test
    @DisplayName("자신이 뿌린건")
    void acquireSelfTest() throws Exception {
        JsonNode resultJson = acquireSprinkle(xRoomId, xUserId, token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_20.getCode());
    }

    @Test
    @DisplayName("다른방에서 발생한 뿌리기일 경우")
    void acquireAnotherRoom() throws Exception {
        JsonNode resultJson = acquireSprinkle("AAA", xUserId, token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_23.getCode());
    }

    @Test
    @DisplayName("중복 줍기 테스트 ( 동일한 줍기를 2회 요청하여 테스트 )")
    void acquireDistinctTest() throws Exception {
        JsonNode resultJson;
        int responseCode;

        resultJson = acquireSprinkle(xRoomId, "1024", token);
        responseCode = resultJson.get("code").asInt();
        assertEquals(responseCode, ResponseCode.CODE_0.getCode());

        resultJson = acquireSprinkle(xRoomId, "1024", token);
        responseCode = resultJson.get("code").asInt();
        assertEquals(responseCode, ResponseCode.CODE_21.getCode());
    }
}
