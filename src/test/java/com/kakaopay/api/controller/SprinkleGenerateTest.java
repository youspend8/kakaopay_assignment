package com.kakaopay.api.controller;

import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SprinkleGenerateTest extends AbstractSprinkleControllerTest {
    private String xRoomId = "294583";
    private String xUserId = "999";

    @Test
    void generateTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/generate")
                .characterEncoding("UTF-8")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("money", String.valueOf(1000))
                .param("division", String.valueOf(2)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        JsonNode resultJson = convertStringToJson(mvcResult.getResponse().getContentAsString());

        assertTrue(resultJson.has("token"));
    }
}
