package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.question.dto.QuestionCommentPatchDto;
import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionCommentService;
import com.preproject.server.user.entity.User;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(QuestionCommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class QuestionCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionCommentService questionCommentService;

    @Test
    @DisplayName("질문의 코멘트 생성 Controller TEST")
    @WithMockUser
    void postQuestionComment() throws Exception {
        // Given
        Long questionId = 1L;
        QuestionCommentPostDto postDto = createPostDto();
        QuestionComment testQuestionComment = createTestQuestionComment();
        QuestionCommentResponseDto responseDto = createResponseDto(testQuestionComment);
        // When
        given(questionMapper.questionCommentDtoToEntity(any(QuestionCommentPostDto.class)))
                .willReturn(testQuestionComment);
        given(questionCommentService.save(any(QuestionComment.class), anyLong(), anyLong()))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentToQuestionCommentResponseDto(any(QuestionComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/question-comment/"+ questionId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.comment").value(responseDto.getComment()));
    }

    @Test
    @DisplayName("질문의 코멘트 수정 Controller TEST")
    @WithMockUser
    void patchQuestionComment() throws Exception {
        // Given
        Long questionCommentId = 1L;
        QuestionCommentPatchDto patchDto = createPatchDto();
        QuestionComment testQuestionComment = createTestQuestionComment();
        QuestionCommentResponseDto responseDto = createResponseDto(testQuestionComment);
        // When
        given(questionCommentService.patch(any(QuestionComment.class), anyLong(), anyLong()))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentPatchDtoToEntity(any(QuestionCommentPatchDto.class)))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentToQuestionCommentResponseDto(any(QuestionComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-comment/comment/"+ questionCommentId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.comment").value(responseDto.getComment()));
    }

    @Test
    @DisplayName("질문의 코멘트 삭제 Controller TEST")
    @WithMockUser
    void deleteQuestionComment() throws Exception {
        // Given
        Long questionCommentId = 1L;
        // When
        doNothing().when(questionCommentService).delete(anyLong());
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/question-comment/comment/"+ questionCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());

    }

    private QuestionComment createTestQuestionComment() {
        QuestionComment entity = new QuestionComment();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        entity.setComment("Test Comment");
        entity.setQuestion(createTestQuestion());
        entity.setUser(user);
        return entity;
    }

    private Question createTestQuestion() {
        Question question = new Question();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        question.setUser(user);
        question.setBody("testBody");
        question.setTitle("testTitle");
        question.setQuestionStatus(QuestionStatus.OPENED);
        return question;
    }

    private QuestionCommentResponseDto createResponseDto(QuestionComment qc) {
        QuestionCommentResponseDto dto = new QuestionCommentResponseDto();
        dto.setQuestionCommentId(1L);
        dto.setUserId(qc.getUser().getUserId());
        dto.setDisplayName(qc.getUser().getDisplayName());
        dto.setComment(qc.getComment());
        return dto;
    }

    private QuestionCommentPostDto createPostDto() {
        QuestionCommentPostDto dto = new QuestionCommentPostDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    private QuestionCommentPatchDto createPatchDto() {
        QuestionCommentPatchDto dto = new QuestionCommentPatchDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

}