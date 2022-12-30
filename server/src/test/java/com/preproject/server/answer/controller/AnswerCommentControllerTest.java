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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
                .post("/answer-comment/{answerId}", answerId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.comment").value(responseDto.getComment()))
                .andDo(MockMvcRestDocumentation.document("postAnswerComment",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("answerId").description("답변 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("comment").type(JsonFieldType.STRING).description("코멘트 내용"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerCommentId").type(JsonFieldType.NUMBER).description("답변 코멘트 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("답변 코멘트 회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("답변 코멘트 회원 닉네임"),
                                        fieldWithPath("data.comment").type(JsonFieldType.STRING).description("코멘트 내용"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                )

                        )
                ));
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
                .patch("/answer-comment/comment/{answerCommentId}", answerCommentId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.comment").value(responseDto.getComment()))
                .andDo(MockMvcRestDocumentation.document("patchAnswerComment",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("answerCommentId").description("답변 코멘트 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("comment").type(JsonFieldType.STRING).description("코멘트 내용").optional(),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자").optional()
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerCommentId").type(JsonFieldType.NUMBER).description("답변 코멘트 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("답변 코멘트 회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("답변 코멘트 회원 닉네임"),
                                        fieldWithPath("data.comment").type(JsonFieldType.STRING).description("코멘트 내용"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                )

                        )
                ));
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
                .delete("/answer-comment/comment/{answerCommentId}", answerCommentId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteAnswerComment",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("answerCommentId").description("답변의 코멘트 식별자")
                                )));
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
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
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