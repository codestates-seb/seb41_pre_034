package com.preproject.server.user.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.mapper.UserMapper;
import com.preproject.server.user.mapper.custom.CustomUserMapper;
import com.preproject.server.user.service.UserService;
import com.preproject.server.util.ApiDocumentUtils;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.headers.HeaderDocumentation;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @MockBean
    private CustomUserMapper customUserMapper;


    @Test
    @DisplayName("사용자 생성 Controller TEST")
    void createUser() throws Exception {
        // Given
        UserPostDto postDto = createPostDto();
        User user = createTestUser(postDto);
        user.setUserId(1L);
        user.setLoginType(LoginType.BASIC);
        user.setUserStatus(UserStatus.ACTIVITY);
        user.setRoles(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        UserSimpleResponseDto simpleResponseDto = createSimpleResponseDto(user);
        // When
        given(userMapper.UserPostDtoToEntity(any(UserPostDto.class))).willReturn(user);
        given(userService.createUser(any(User.class))).willReturn(user);
        given(userMapper.userEntityToSimpleResponseDto(any(User.class))).willReturn(simpleResponseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders.post("/users")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(postDto.getEmail()))
                .andDo(MockMvcRestDocumentation.document("postUsers",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호"),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("emailNotice").type(JsonFieldType.BOOLEAN).description("이메일 알림 여부")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.emailNotice").type(JsonFieldType.BOOLEAN).description("이메일 알림 여부"),
                                        fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("회원 로그인 타입"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                )

                        )
                ));

    }

    @Test
    @DisplayName("사용자 단건 조회 Controller TEST")
    @WithMockUser
    void getUser() throws Exception {
        // Given
        User testUser = createTestUser();
        UserResponseDto userResponseDto = createUserResponseDto(testUser);

        // When
        given(userService.verifiedUserById(anyLong())).willReturn(testUser);
        given(customUserMapper.userEntityToResponseDto(any(User.class)))
                .willReturn(userResponseDto);
        RequestBuilder result = RestDocumentationRequestBuilders.get("/users/{userId}" ,testUser.getUserId())
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(userResponseDto.getEmail()))
                .andDo(MockMvcRestDocumentation.document("getUser",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("userId").description("회원 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.emailNotice").type(JsonFieldType.BOOLEAN).description("이메일 알림 여부"),
                                        fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("회원 로그인 타입"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.questions").type(JsonFieldType.ARRAY).description("작성 질문 목록"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("작성 답변 목록"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("작성 질문에 포함된 태그 목록")
                                ))));

    }

    @Test
    @DisplayName("사용자 전체 조회 Controller TEST")
    void getUsers() throws Exception {
        // Given
        User testUser = createTestUser();
        UserSimpleResponseDto simpleResponseDto = createSimpleResponseDto(testUser);
        // When
        given(userService.findUsers(any(Pageable.class))).willReturn(
                new PageImpl<>(List.of(testUser, testUser), PageRequest.of(0, 10), 2)
        );
        given(userMapper.UserListToResponseDtoList(anyList()))
                .willReturn(List.of(simpleResponseDto,simpleResponseDto));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/users?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].email").value(simpleResponseDto.getEmail()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(
                        MockMvcRestDocumentation.document("getUsers",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                RequestDocumentation.requestParameters(
                                        parameterWithName("page").description("요청 페이지(0부터 1페이지)")
                                ),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                                fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                                fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
                                                fieldWithPath("data[].displayName").type(JsonFieldType.STRING).description("닉네임"),
                                                fieldWithPath("data[].emailNotice").type(JsonFieldType.BOOLEAN).description("이메일 알림 여부"),
                                                fieldWithPath("data[].userStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                                fieldWithPath("data[].loginType").type(JsonFieldType.STRING).description("회원 로그인 타입"),
                                                fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                                fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                                fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지"),
                                                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")
                                                ))));
    }

    @Test
    @DisplayName("사용자 정보 수정 Controller TEST")
    @WithMockUser
    void patchUser() throws Exception {
        // Given
        UserPatchDto patchDto = createPatchDto();
        User testUser = createTestUser();
        testUser.setDisplayName(patchDto.getDisplayName());
        UserSimpleResponseDto simpleResponseDto = createSimpleResponseDto(testUser);
        // When
        given(userMapper.UserPatchDtoToEntity(any(UserPatchDto.class))).willReturn(testUser);
        given(userService.updateUser(any(User.class))).willReturn(testUser);
        given(userMapper.userEntityToSimpleResponseDto(any(User.class))).willReturn(simpleResponseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders.patch("/users/{userId}",testUser.getUserId())
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(simpleResponseDto.getEmail()))
                .andExpect(jsonPath("$.data.displayName").value(simpleResponseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.userId").value(simpleResponseDto.getUserId()))
                .andDo(MockMvcRestDocumentation.document("patchUser",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("userId").description("회원 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("email").type(JsonFieldType.STRING).description("이메일").optional(),
                                        fieldWithPath("password").type(JsonFieldType.STRING).description("비밀 번호").optional(),
                                        fieldWithPath("displayName").type(JsonFieldType.STRING).description("닉네임").optional()
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.emailNotice").type(JsonFieldType.BOOLEAN).description("이메일 알림 여부"),
                                        fieldWithPath("data.userStatus").type(JsonFieldType.STRING).description("회원 상태"),
                                        fieldWithPath("data.loginType").type(JsonFieldType.STRING).description("회원 로그인 타입"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                ))));

    }

    @Test
    @DisplayName("사용자 삭제 Controller TEST")
    @WithMockUser
    void deleteUser() throws Exception {
        // Given
        Long userId = 1L;
        // When
        doNothing().when(userService).deleteUser(userId);
        RequestBuilder result = RestDocumentationRequestBuilders.delete("/users/{userId}",userId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteUser",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("userId").description("회원 식별자")
                                )));

    }

    private UserPostDto createPostDto() {
        return new UserPostDto(
                "testaa@test.com",
                "1111!",
                "testUser",
                true);
    }

    private UserPatchDto createPatchDto() {
        UserPatchDto userPatchDto = new UserPatchDto();
        userPatchDto.setDisplayName("patchUser");
        return userPatchDto;
    }


    private User createTestUser() {
        UserPostDto postDto = createPostDto();
        User user = createTestUser(postDto);
        user.setUserId(1L);
        user.setLoginType(LoginType.BASIC);
        user.setUserStatus(UserStatus.ACTIVITY);
        user.setRoles(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        return user;
    }

    private User createTestUser(UserPostDto userPostDto) {
        User user = new User();

        user.setEmail( userPostDto.getEmail() );
        user.setPassword( userPostDto.getPassword() );
        user.setDisplayName( userPostDto.getDisplayName() );
        user.setEmailNotice( userPostDto.getEmailNotice() );
        return user;
    }

    private UserSimpleResponseDto createSimpleResponseDto(User user) {

        UserSimpleResponseDto userSimpleResponseDto = new UserSimpleResponseDto();

        userSimpleResponseDto.setUserId( user.getUserId() );
        userSimpleResponseDto.setEmail( user.getEmail() );
        userSimpleResponseDto.setDisplayName( user.getDisplayName() );
        userSimpleResponseDto.setEmailNotice( user.getEmailNotice() );
        if ( user.getUserStatus() != null ) {
            userSimpleResponseDto.setUserStatus( user.getUserStatus().name() );
        }
        if ( user.getLoginType() != null ) {
            userSimpleResponseDto.setLoginType( user.getLoginType().name() );
        }
        userSimpleResponseDto.setCreateAt(LocalDateTime.now());
        userSimpleResponseDto.setUpdateAt(LocalDateTime.now());
        return userSimpleResponseDto;
    }

    private UserResponseDto createUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setDisplayName(user.getDisplayName());
        dto.setEmailNotice(user.getEmailNotice());
        dto.setUserStatus(user.getUserStatus().name());
        dto.setLoginType(user.getLoginType().name());
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setQuestions(List.of());
        dto.setAnswers(List.of());
        dto.setTags(List.of());
        return dto;
    }

}