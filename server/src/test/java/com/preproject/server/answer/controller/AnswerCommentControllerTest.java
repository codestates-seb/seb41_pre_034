package com.preproject.server.answer.controller;

import com.google.gson.Gson;
import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerCommentService;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.question.entity.Question;
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

@WebMvcTest(AnswerCommentController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class AnswerCommentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerCommentService answerCommentService;

    @MockBean
    private AnswerMapper answerMapper;

    @Test
    @DisplayName("답변의 코멘트 생성 Controller TEST")
    @WithMockUser
    void postComment() throws Exception {
        // Given
        Long answerId = 1L;
        AnswerCommentPostDto postDto = createPostDto();
        AnswerComment testAnswerComment = createTestAnswerComment();
        AnswerCommentResponseDto responseDto = createResponseDto(testAnswerComment);
        // When
        given(answerCommentService.createComment(any(AnswerComment.class), anyLong(), anyLong()))
                .willReturn(testAnswerComment);
        given(answerMapper.AnswerPostDtoToEntity(any(AnswerCommentPostDto.class)))
                .willReturn(testAnswerComment);
        given(answerMapper.answerCommentToAnswerCommentResponseDto(any(AnswerComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/answer-comment/"+answerId)
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
    @DisplayName("답변의 코멘트 수정 Controller TEST")
    @WithMockUser
    void patchComment() throws Exception {
        // Given
        Long answerCommentId = 1L;
        AnswerCommentPatchDto patchDto = createPatchDto();
        AnswerComment testAnswerComment = createTestAnswerComment();
        AnswerCommentResponseDto responseDto = createResponseDto(testAnswerComment);
        // When
        given(answerMapper.AnswerPatchDtoToEntity(any(AnswerCommentPatchDto.class)))
                .willReturn(testAnswerComment);
        given(answerCommentService.updateComment(any(AnswerComment.class), anyLong()))
                .willReturn(testAnswerComment);
        given(answerMapper.answerCommentToAnswerCommentResponseDto(any(AnswerComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/answer-comment/comment/"+answerCommentId)
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
    @DisplayName("답변의 코멘트 삭제 Controller TEST")
    @WithMockUser
    void deleteComment() throws Exception {
        // Given
        Long answerCommentId = 1L;
        // When
        doNothing().when(answerCommentService).delete(anyLong());
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/answer-comment/comment/"+answerCommentId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());
    }


    private AnswerCommentPostDto createPostDto() {
        AnswerCommentPostDto dto = new AnswerCommentPostDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    private AnswerCommentPatchDto createPatchDto() {
        AnswerCommentPatchDto dto = new AnswerCommentPatchDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    private AnswerCommentResponseDto createResponseDto(AnswerComment ac) {
        AnswerCommentResponseDto dto = new AnswerCommentResponseDto();
        dto.setAnswerCommentId(1L);
        dto.setUserId(ac.getUser().getUserId());
        dto.setDisplayName(ac.getUser().getDisplayName());
        dto.setComment(ac.getComment());
        return dto;

    }

    private AnswerComment createTestAnswerComment() {
        AnswerComment entity = new AnswerComment();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        entity.setComment("Test Comment");
        entity.setAnswer(createTestAnswer());
        entity.setUser(user);
        return entity;
    }

    private Answer createTestAnswer() {
        Answer answer = new Answer();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        answer.setUser(user);
        answer.setBody("Test Answer");
        answer.setCheck(false);
        answer.setQuestion(createTestQuestion());
        return answer;
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

}