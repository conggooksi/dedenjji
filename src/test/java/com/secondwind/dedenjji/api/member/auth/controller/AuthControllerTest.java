package com.secondwind.dedenjji.api.member.auth.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonParser;
import com.secondwind.dedenjji.api.member.auth.domain.request.TokenRequestDTO;
import com.secondwind.dedenjji.api.member.domain.request.SignUpRequest;
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
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

import java.util.Base64;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
public class AuthControllerTest {
    @Autowired
    MockMvc mvc;

    private final static String BASE_URL = "/api/auth";
    ObjectMapper objectMapper = new ObjectMapper();

    public String getAccessToken(MockMvc mvc) throws Exception {
        String url = "/login";
        String username = "cong1";
        String password = "Qwpo1209";


        MvcResult mvcResult = mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        return JsonParser.parseString(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data")
                .getAsJsonObject().get("accessToken").getAsString();
    }

    public TokenRequestDTO getTokenRequestDTO(MockMvc mvc) throws Exception {
        String url = "/login";
        String username = "cong1";
        String password = "Qwpo1209";


        MvcResult mvcResult = mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION,
                                "Basic " + Base64.getEncoder().encodeToString((username + ":" + password).getBytes())))
                .andExpect(status().is2xxSuccessful())
                .andReturn();

        String accessToken = JsonParser.parseString(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data")
                .getAsJsonObject().get("accessToken").getAsString();

        String refreshToken = JsonParser.parseString(mvcResult.getResponse().getContentAsString()).getAsJsonObject().get("data")
                .getAsJsonObject().get("refreshToken").getAsString();
        return new TokenRequestDTO(accessToken, refreshToken);
    }

    @Test
    @Transactional
    @DisplayName("회원 가입")
    public void signUpTest() throws Exception {
        String url = "/signup";
        String loginId = "cong6";
        String password = "Qwpo1209";

        String originalInput = loginId + ":" + password;
        String encodedLoginInfo = Base64.getEncoder().encodeToString(originalInput.getBytes());

        SignUpRequest memberJoinDTO = new SignUpRequest("", "", "콩국시6");
        String requestBody = objectMapper.writeValueAsString(memberJoinDTO);

        mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedLoginInfo)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그인")
    public void login() throws Exception {
        String url = "/login";
        String loginId = "cong4";
        String password = "Qwpo1209";

        String originalInput = loginId + ":" + password;
        String encodedLoginInfo = Base64.getEncoder().encodeToString(originalInput.getBytes());

        mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Basic " + encodedLoginInfo)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("로그아웃")
    public void logout() throws Exception {
        String url = "/logout";
        String accessToken = getAccessToken(mvc);
        TokenRequestDTO tokenDTO = getTokenRequestDTO(mvc);
        String requestBody = objectMapper.writeValueAsString(tokenDTO);

        mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("리이슈")
    public void reissue() throws Exception {
        String url = "/reissue";
        String accessToken = getAccessToken(mvc);
        TokenRequestDTO tokenDTO = getTokenRequestDTO(mvc);
        String requestBody = objectMapper.writeValueAsString(tokenDTO);

        mvc.perform(post(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }
}