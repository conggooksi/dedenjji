package com.secondwind.dedenjji.api.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondwind.dedenjji.api.member.auth.controller.AuthControllerTest;
import com.secondwind.dedenjji.api.member.domain.request.UpdateNicknameRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class MemberControllerTest {

    private final static String BASE_URL = "/api/members";
    @Autowired
    MockMvc mvc;

    ObjectMapper objectMapper = new ObjectMapper();
    AuthControllerTest authControllerTest = new AuthControllerTest();
    String accessToken;

    @BeforeEach
    public void getAccessToken() throws Exception {
        accessToken = authControllerTest.getAccessToken(mvc);
    }

    @Test
    @Transactional
    @DisplayName("닉네임 변경")
    void updateNickname() throws Exception {
        // given
        String url = "/nickname";
        String nickname = "cong1";

        UpdateNicknameRequest updateNicknameRequest = new UpdateNicknameRequest();
        updateNicknameRequest.setNickname(nickname);

        String requestBody = objectMapper.writeValueAsString(updateNicknameRequest);

        // when
        mvc.perform(patch(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}