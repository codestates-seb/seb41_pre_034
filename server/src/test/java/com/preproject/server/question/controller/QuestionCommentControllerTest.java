package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.question.dto.QuestionCommentPatchDto;
import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionCommentService;
import com.preproject.server.util.ApiDocumentUtils;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import com.preproject.server.utils.TestStub;
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
        QuestionCommentPostDto postDto = TestStub.createQuestionCommentPostDto();
        QuestionComment testQuestionComment = TestStub.createTestQuestionComment();
        QuestionCommentResponseDto responseDto = TestStub.createQuestionCommentResponseDto();
        // When
        given(questionMapper.questionCommentDtoToEntity(any(QuestionCommentPostDto.class)))
                .willReturn(testQuestionComment);
        given(questionCommentService.save(any(QuestionComment.class), anyLong(), anyLong()))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentToQuestionCommentResponseDto(any(QuestionComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/question-comment/{questionId}", questionId)
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
                .andDo(MockMvcRestDocumentation.document("postQuestionComment",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("questionId").description("질문 식별자")
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
                                        fieldWithPath("data.questionCommentId").type(JsonFieldType.NUMBER).description("질문 코멘트 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("질문 코멘트 회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("질문 코멘트 회원 닉네임"),
                                        fieldWithPath("data.comment").type(JsonFieldType.STRING).description("코멘트 내용"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                )

                        )
                ));
    }

    @Test
    @DisplayName("질문의 코멘트 수정 Controller TEST")
    @WithMockUser
    void patchQuestionComment() throws Exception {
        // Given
        Long questionCommentId = 1L;
        QuestionCommentPatchDto patchDto = TestStub.createQuestionCommentPatchDto();
        QuestionComment testQuestionComment = TestStub.createTestQuestionComment();
        QuestionCommentResponseDto responseDto = TestStub.createQuestionCommentResponseDto();
        // When
        given(questionCommentService.patch(any(QuestionComment.class), anyLong(), anyLong()))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentPatchDtoToEntity(any(QuestionCommentPatchDto.class)))
                .willReturn(testQuestionComment);
        given(questionMapper.questionCommentToQuestionCommentResponseDto(any(QuestionComment.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-comment/comment/{questionCommentId}", questionCommentId)
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
                .andDo(MockMvcRestDocumentation.document("patchQuestionComment",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("questionCommentId").description("질문 코멘트 식별자")
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
                                        fieldWithPath("data.questionCommentId").type(JsonFieldType.NUMBER).description("질문 코멘트 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("질문 코멘트 회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("질문 코멘트 회원 닉네임"),
                                        fieldWithPath("data.comment").type(JsonFieldType.STRING).description("코멘트 내용"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각")
                                )

                        )
                ));
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
                .delete("/question-comment/comment/{questionCommentId}", questionCommentId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteQuestionComment",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("questionCommentId").description("질문의 코멘트 식별자")
                                )));

    }
}