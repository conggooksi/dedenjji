package com.secondwind.dedenjji.api.club.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AddClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.AllowClubMemberRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.ChangeClubMemberLevelRequest;
import com.secondwind.dedenjji.api.club.clubMember.domain.request.DeleteClubMemberRequest;
import com.secondwind.dedenjji.api.club.domain.request.CreateClubRequest;
import com.secondwind.dedenjji.api.club.domain.request.UpdateClubRequest;
import com.secondwind.dedenjji.api.member.auth.controller.AuthControllerTest;
import com.secondwind.dedenjji.common.enumerate.Level;
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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Slf4j
class ClubControllerTest {
    private final static String BASE_URL = "/api/clubs";
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
    @DisplayName("클럽 생성")
    void createClub() throws Exception {
        String url = "/new";

        CreateClubRequest createClubRequest = new CreateClubRequest();
        createClubRequest.setName("테스트 클럽");
        createClubRequest.setLevel(Level.JOONGSU);

        String requestBody = objectMapper.writeValueAsString(createClubRequest);

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
    @DisplayName("클럽 슬라이스 조회")
    void getClubs() throws Exception {
        String url = "";

        mvc.perform(get(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 상세 조회")
    void getClub() throws Exception {
        String url = "/12";

        mvc.perform(get(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 수정")
    void updateClub() throws Exception {
        UpdateClubRequest updateClubRequest = new UpdateClubRequest();
        updateClubRequest.setId(12L);
        updateClubRequest.setName("테스트 클럽2");

        String requestBody = objectMapper.writeValueAsString(updateClubRequest);

        String url = "";

        mvc.perform(patch(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 삭제")
    void deleteClub() throws Exception {

        String url = "/12";

        mvc.perform(delete(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 멤버 추가")
    void addClubMember() throws Exception {
        String url = "/new_member";
        AddClubMemberRequest addClubMemberRequest = new AddClubMemberRequest();
        addClubMemberRequest.setClubId(12L);
        addClubMemberRequest.setMemberId(5L);

        String requestBody = objectMapper.writeValueAsString(addClubMemberRequest);

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
    @DisplayName("클럽 멤버 허용")
    void makeIsAllow() throws Exception {
        String url = "/allow";
        AllowClubMemberRequest allowClubMemberRequest = new AllowClubMemberRequest();
        allowClubMemberRequest.setClubId(12L);
        allowClubMemberRequest.setMemberId(2L);

        String requestBody = objectMapper.writeValueAsString(allowClubMemberRequest);

        mvc.perform(patch(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 멤버 삭제")
    void deleteClubMember() throws Exception {
        String url = "/delete";
        DeleteClubMemberRequest deleteClubMemberRequest = new DeleteClubMemberRequest();
        deleteClubMemberRequest.setClubId(12L);
        deleteClubMemberRequest.setMemberId(2L);

        String requestBody = objectMapper.writeValueAsString(deleteClubMemberRequest);

        mvc.perform(delete(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

    @Test
    @Transactional
    @DisplayName("클럽 멤버 레벨 변경")
    void changeClubMemberLevel() throws Exception {
        String url = "/level";
        ChangeClubMemberLevelRequest changeClubMemberAuthorityRequest = new ChangeClubMemberLevelRequest();
        changeClubMemberAuthorityRequest.setClubId(12L);
        changeClubMemberAuthorityRequest.setMemberId(2L);
        changeClubMemberAuthorityRequest.setLevel(Level.GOSU);

        String requestBody = objectMapper.writeValueAsString(changeClubMemberAuthorityRequest);

        mvc.perform(patch(BASE_URL + url)
                        .header(HttpHeaders.ACCEPT, "application/json;charset=UTF-8")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is2xxSuccessful())
                .andDo(print());
    }

}