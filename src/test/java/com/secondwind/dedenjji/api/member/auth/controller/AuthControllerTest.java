package com.secondwind.dedenjji.api.member.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondwind.dedenjji.api.member.domain.dto.MemberJoinDTO;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    private final static String BASE_URL = "/api/auth";
    ObjectMapper objectMapper = new ObjectMapper();

    @Test
//    @Transactional
    @DisplayName("회원 가입")
    public void signUpTest() throws Exception {
        String url = "/signup";
        String loginId = "cong5";
        String password = "Qwpo1209";

        String originalInput = loginId + ":" + password;
        String encodedLoginInfo = Base64.getEncoder().encodeToString(originalInput.getBytes());

        MemberJoinDTO memberJoinDTO = new MemberJoinDTO("", "", "콩국시5");
        String requestBody = objectMapper.writeValueAsString(memberJoinDTO);

        mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedLoginInfo)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}