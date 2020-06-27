package com.kakaopay.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.kakaopay.api.entity.SprinkleVO;
import com.kakaopay.api.helper.response.ResponseCode;
import com.kakaopay.api.repository.SprinkleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("뿌리기 줍기 테스트")
public class SprinkleAcquireTestToDataUpdate extends AbstractSprinkleControllerTest {
    private static String token;
    private static String xRoomId = "294583";
    private static String xUserId = "999";

    @Autowired
    private SprinkleRepository sprinkleRepository;

    @BeforeEach
    void beforeAcquire() throws Exception {
        JsonNode resultJson = this.generateSprinkle(xRoomId, xUserId, 1000, 2);

        token = resultJson.get("data").asText();
    }

    @Test
    @DisplayName("뿌리기 이후 10분이 경과된 경우")
    void acquireTest() throws Exception {
        setPast10Minute();

        JsonNode resultJson = acquireSprinkle(xRoomId, "1024", token);

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_22.getCode());
    }

    private void setPast10Minute() {
        SprinkleVO sprinkleVO = sprinkleRepository.findById(token).get();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(sprinkleVO.getCreateDate().toInstant(), ZoneId.systemDefault()).plusMinutes(-10);
        sprinkleVO.setCreateDate(Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant()));
        sprinkleRepository.save(sprinkleVO);
    }
}
