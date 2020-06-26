package com.kakaopay.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kakaopay.api.helper.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
public class SprinkleInfoTest extends AbstractSprinkleControllerTest {
    private String token;
    private String xRoomId = "294583";
    private String xUserId = "999";

    @BeforeEach
    void beforeInfo() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/generate")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("money", String.valueOf(1000))
                .param("division", String.valueOf(2)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        JsonNode resultJson = convertStringToJson(mvcResult.getResponse().getContentAsString());
        token = resultJson.get("data").asText();
    }

    @Test
    void infoTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/info")
                .characterEncoding("UTF-8")
                .contentType("application/json; charset=UTF-8")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("token", token))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        JsonNode resultJson = convertStringToJson(mvcResult.getResponse().getContentAsString());

        int responseCode = resultJson.get("code").asInt();

        assertEquals(responseCode, ResponseCode.CODE_0.getCode());
    }
}
