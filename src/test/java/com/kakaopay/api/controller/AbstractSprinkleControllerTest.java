package com.kakaopay.api.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

public abstract class AbstractSprinkleControllerTest {
    @Autowired
    protected MockMvc mockMvc;

    protected static JsonNode convertStringToJson(String str) throws JsonProcessingException {
        return new ObjectMapper().readTree(str);
    }
}