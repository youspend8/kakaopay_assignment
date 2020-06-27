package com.kakaopay.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

public abstract class AbstractSprinkleControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected static JsonNode convertStringToJson(String str) throws JsonProcessingException {
        return new ObjectMapper().readTree(str);
    }

    /**
     * 공통적으로 사용하는 뿌리기 생성 요청
     * @param xRoomId
     * @param xUserId
     * @param money
     * @param division
     * @return Token - 뿌리기 시 발급된 토큰
     * @throws Exception
     */
    protected JsonNode generateSprinkle(String xRoomId, String xUserId, int money, int division) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/generate")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("money", String.valueOf(money))
                .param("division", String.valueOf(division)))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        return convertStringToJson(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 공통적으로 사용하는 줍기 요청
     * @param xRoomId - 요청 헤더 X-ROOM-ID
     * @param xUserId - 요청 헤더 X-USER-ID
     * @param token - 뿌리기 시 발급된 토큰
     * @return Token - 줍기 성공한 금액
     * @throws Exception
     */
    protected JsonNode acquireSprinkle(String xRoomId, String xUserId, String token) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/acquire")
                .characterEncoding("UTF-8")
                .contentType("application/json; charset=UTF-8")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("token", token))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        return convertStringToJson(mvcResult.getResponse().getContentAsString());
    }

    /**
     * 공통적으로 사용하는 뿌리기 조회 요청
     * @param xRoomId - 요청 헤더 X-ROOM-ID
     * @param xUserId - 요청 헤더 X-USER-ID
     * @param token - 뿌리기 시 발급된 토큰
     * @return SprinkleVO - 뿌리기 정보
     * @throws Exception
     */
    protected JsonNode infoSprinkle(String xRoomId, String xUserId, String token) throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/sprinkle/info")
                .characterEncoding("UTF-8")
                .contentType("application/json; charset=UTF-8")
                .header("X-ROOM-ID", xRoomId)
                .header("X-USER-ID", xUserId)
                .param("token", token))
                        .andExpect(MockMvcResultMatchers.status().isOk())
                        .andDo(MockMvcResultHandlers.print())
                        .andReturn();

        return convertStringToJson(mvcResult.getResponse().getContentAsString());
    }
}