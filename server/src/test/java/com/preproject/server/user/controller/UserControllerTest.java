//package com.preproject.server.user.controller;
//
//import com.google.gson.Gson;
//import com.preproject.server.constant.LoginType;
//import com.preproject.server.constant.UserStatus;
//import com.preproject.server.home.HomeController;
//import com.preproject.server.user.dto.UserPostDto;
//import com.preproject.server.user.dto.UserSimpleResponseDto;
//import com.preproject.server.user.entity.User;
//import com.preproject.server.user.mapper.UserMapper;
//import com.preproject.server.user.mapper.custom.CustomUserMapper;
//import com.preproject.server.user.service.UserService;
//import com.preproject.server.utils.JwtAuthorityUtils;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//import javax.servlet.Filter;
//import java.nio.charset.StandardCharsets;
//
//import static org.mockito.ArgumentMatchers.any;
//import static org.mockito.BDDMockito.given;
//import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//
//@WebMvcTest(
//        controllers = HomeController.class
//)
//@MockBean(JpaMetamodelMappingContext.class)
//@AutoConfigureRestDocs
//@AutoConfigureMockMvc
//class UserControllerTest {
//
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private Filter springSecurityFilterChain;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private Gson gson;
//
//    @MockBean
//    private UserService userService;
//
//    @MockBean
//    private UserMapper userMapper;
//
//    @MockBean
//    private CustomUserMapper customUserMapper;
//
//
//    @BeforeEach
//    public void setup() {
//        this.mockMvc = MockMvcBuilders
//                .webAppContextSetup(context)
//                .apply(springSecurity())
//                .addFilters(springSecurityFilterChain)
//                .alwaysDo(print())
//                .build();
//    }
//
//
//    @Test
//    @DisplayName("User Controller Create User TEST")
//    void createUser() throws Exception {
//        // Given
//        UserPostDto postDto = createPostDto();
//        User user = createTestUser(postDto);
//        user.setUserId(1L);
//        user.setLoginType(LoginType.BASIC);
//        user.setUserStatus(UserStatus.ACTIVITY);
//        user.setRoles(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
//        UserSimpleResponseDto simpleResponseDto = createSimpleResponseDto(user);
//        // When
//        given(userMapper.UserPostDtoToEntity(any(UserPostDto.class))).willReturn(user);
//        given(userService.createUser(any(User.class))).willReturn(user);
//        given(userMapper.userEntityToSimpleResponseDto(any(User.class))).willReturn(simpleResponseDto);
//        String content = gson.toJson(postDto);
//        MockHttpServletRequestBuilder result = post("/users")
//                .content(content)
//                .contentType(MediaType.APPLICATION_JSON)
//                .accept(MediaType.APPLICATION_JSON)
//                .characterEncoding(StandardCharsets.UTF_8.displayName())
//                .with(csrf());
//        // Then
//        MvcResult mvcResult = mockMvc.perform(result)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.data.email").value(postDto.getEmail()))
//                .andReturn();
//
//    }
//
//    private UserPostDto createPostDto() {
//        return new UserPostDto(
//                "testaa@test.com",
//                "1111!",
//                "testUser",
//                true);
//    }
//
//    private User createTestUser(UserPostDto userPostDto) {
//        User user = new User();
//
//        user.setEmail( userPostDto.getEmail() );
//        user.setPassword( userPostDto.getPassword() );
//        user.setDisplayName( userPostDto.getDisplayName() );
//        user.setEmailNotice( userPostDto.getEmailNotice() );
//        return user;
//    }
//
//    private UserSimpleResponseDto createSimpleResponseDto(User user) {
//
//        UserSimpleResponseDto userSimpleResponseDto = new UserSimpleResponseDto();
//
//        userSimpleResponseDto.setUserId( user.getUserId() );
//        userSimpleResponseDto.setEmail( user.getEmail() );
//        userSimpleResponseDto.setDisplayName( user.getDisplayName() );
//        userSimpleResponseDto.setEmailNotice( user.getEmailNotice() );
//        if ( user.getUserStatus() != null ) {
//            userSimpleResponseDto.setUserStatus( user.getUserStatus().name() );
//        }
//        if ( user.getLoginType() != null ) {
//            userSimpleResponseDto.setLoginType( user.getLoginType().name() );
//        }
//        return userSimpleResponseDto;
//    }
//
//}