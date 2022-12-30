package com.preproject.server.auth.controller;

import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.user.entity.User;
import com.preproject.server.util.ApiDocumentUtils;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import com.preproject.server.utils.Token;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JwtTokenizer jwtTokenizer;


    @Test
    @DisplayName("로그인 상태 확인 TEST - OK")
    @WithMockUser
    void verifyUserOK() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(createTestUser());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/verify")
                .header("Authorization", token.getAccessToken())
                .header("Refresh", token.getRefreshToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("verifyUserOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                )));
    }

    @Test
    @DisplayName("로그인 상태 확인 TEST - AccessToken 만료")
    @WithMockUser
    void verifyUserThrowException() throws Exception {
        // Given
        Token token = createExpiredToken();
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/verify")
                .header("Authorization", token.getAccessToken())
                .header("Refresh", token.getRefreshToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.status").value(403))
                .andExpect(jsonPath("$.message")
                        .value(ErrorCode.EXPIRED_ACCESS_TOKEN.getMessage()))
                .andDo(
                        MockMvcRestDocumentation.document("verifyUserException",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("ErrorCode"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("ErrorMessage"),
                                                fieldWithPath("fieldErrors").type(JsonFieldType.STRING).description("fieldErrors").optional(),
                                                fieldWithPath("violationErrors").type(JsonFieldType.STRING).description("violationErrors").optional()
                                        ))));
    }



    @Test
    @DisplayName("토큰 재발급 TEST - OK")
    @WithMockUser
    void reissuetokenThrowException() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(createTestUser());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/reissuetoken")
                .header("Refresh", token.getRefreshToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("refreshOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                HeaderDocumentation.responseHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                )
                        ));
    }

    @Test
    @DisplayName("토큰 재발급 TEST - RefreshToken 만료")
    @WithMockUser
    void reissuetoken() throws Exception {
        // Given
        Token token = createExpiredToken();
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/reissuetoken")
                .header("Refresh", token.getRefreshToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(401))
                .andExpect(jsonPath("$.message")
                        .value(ErrorCode.EXPIRED_REFRESH_TOKEN.getMessage()))
                .andDo(
                        MockMvcRestDocumentation.document("refreshExpired",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("status").type(JsonFieldType.NUMBER).description("ErrorCode"),
                                                fieldWithPath("message").type(JsonFieldType.STRING).description("ErrorMessage"),
                                                fieldWithPath("fieldErrors").type(JsonFieldType.STRING).description("fieldErrors").optional(),
                                                fieldWithPath("violationErrors").type(JsonFieldType.STRING).description("violationErrors").optional()
                                        ))));
    }

    @Test
    @DisplayName("Logout Controller 동작 TEST")
    @WithMockUser
    void logout() throws Exception {
        // Given
        Token token = jwtTokenizer.delegateToken(createTestUser());
        // When
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/auth/logout")
                .header("Authorization", token.getAccessToken())
                .header("Refresh", token.getRefreshToken())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("logoutOk",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                )));
    }


    private User createTestUser() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("testaa@test.com");
        user.setPassword("1111!");
        user.setDisplayName("testUser");
        user.setEmailNotice(true);
        user.setLoginType(LoginType.BASIC);
        user.setUserStatus(UserStatus.ACTIVITY);
        user.setRoles(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        return user;
    }

    private Token createExpiredToken() {
        Map<String, Object> claims = new HashMap<>();
        User testUser = createTestUser();
        claims.put("username", testUser.getEmail());
        claims.put("roles", testUser.getRoles());

        String subject = testUser.getEmail();

        String base64SecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());
        Key key = jwtTokenizer.getKeyFromBase64EncodedSecretKey(base64SecretKey);
        return new Token(
                "Bearer " + Jwts.builder()
                        .setClaims(claims)
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(0))
                        .signWith(key)
                        .compact(),
                Jwts.builder()
                        .setSubject(subject)
                        .setIssuedAt(Calendar.getInstance().getTime())
                        .setExpiration(jwtTokenizer.getTokenExpiration(0))
                        .signWith(key)
                        .compact());
    }

}