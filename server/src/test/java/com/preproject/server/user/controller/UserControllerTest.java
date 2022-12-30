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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(postDto.getEmail()));

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
        RequestBuilder result = RestDocumentationRequestBuilders.get("/users/" + testUser.getUserId())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(userResponseDto.getEmail()));

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
        RequestBuilder result = RestDocumentationRequestBuilders.get("/users")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].email").value(simpleResponseDto.getEmail()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));
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
        RequestBuilder result = RestDocumentationRequestBuilders.patch("/users/"+testUser.getUserId())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(simpleResponseDto.getEmail()))
                .andExpect(jsonPath("$.data.displayName").value(simpleResponseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.userId").value(simpleResponseDto.getUserId()));
    }

    @Test
    @DisplayName("사용자 삭제 Controller TEST")
    @WithMockUser
    void deleteUser() throws Exception {
        // Given
        Long userId = 1L;
        // When
        doNothing().when(userService).deleteUser(userId);
        RequestBuilder result = RestDocumentationRequestBuilders.delete("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());

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
        dto.setCreateAt(LocalDateTime.now());
        dto.setQuestions(List.of());
        dto.setAnswers(List.of());
        dto.setTags(List.of());
        return dto;
    }

}