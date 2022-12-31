package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionVoteService;
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
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(QuestionVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class QuestionVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private QuestionVoteService questionVoteService;

    @Test
    @DisplayName("질문의 추천 생성 Controller TEST")
    @WithMockUser
    void postQuestionVote() throws Exception {
        // Given
        Long questionId = 1L;
        QuestionVotePostDto postDto = TestStub.createQuestionVotePostDto();
        QuestionVote testQuestionVote = TestStub.createTestQuestionVote();
        QuestionVoteResponseDto responseDto = TestStub.createQuestionVoteResponseDto();
        // When
        given(questionMapper.questionVotePostDtoToEntity(any(QuestionVotePostDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.createVote(any(QuestionVote.class), anyLong(), anyLong()))
                .willReturn(testQuestionVote);
        given(questionMapper.questionVoteToQuestionVoteResponseDto(any(QuestionVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/question-vote/{questionId}", questionId)
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
                .andExpect(jsonPath("$.data.voteStatus").value(responseDto.getVoteStatus()))
                .andExpect(jsonPath("$.data.questionVoteId").value(responseDto.getQuestionVoteId()))
                .andDo(MockMvcRestDocumentation.document("postQuestionVote",
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
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("voteStatus").type(JsonFieldType.STRING).description("추천 상태 (UP/DOWN)")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionVoteId").type(JsonFieldType.NUMBER).description("질문 추천 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("질문 추천 회원 식별자"),
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("질문 추천 상태(UP/NONE/DOWN)")
                                )

                        )));
    }

    @Test
    @DisplayName("질문의 추천 수정 Controller TEST - 변경사항 있을 경우")
    @WithMockUser
    void patchQuestionVote_OK_case() throws Exception {
        // Given
        Long questionVoteId = 1L;
        QuestionVotePatchDto patchDto = TestStub.createQuestionVotePatchDto();
        QuestionVote testQuestionVote = TestStub.createTestQuestionVote();
        QuestionVoteResponseDto responseDto = TestStub.createQuestionVoteResponseDto();
        // When
        given(questionMapper.questionVotePatchDtoToEntity(any(QuestionVotePatchDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.updateVote(any(QuestionVote.class), anyLong()))
                .willReturn(testQuestionVote);
        given(questionMapper.questionVoteToQuestionVoteResponseDto(any(QuestionVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-vote/vote/{questionVoteId}", questionVoteId)
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
                .andExpect(jsonPath("$.data.voteStatus").value(responseDto.getVoteStatus()))
                .andExpect(jsonPath("$.data.questionVoteId").value(responseDto.getQuestionVoteId()))
                .andDo(MockMvcRestDocumentation.document("patchOkQuestionVote",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("questionVoteId").description("질문 추천 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("voteStatus").type(JsonFieldType.STRING).description("추천 상태 (UP/DOWN)")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionVoteId").type(JsonFieldType.NUMBER).description("질문 추천 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("질문 추천 회원 식별자"),
                                        fieldWithPath("data.voteStatus").type(JsonFieldType.STRING).description("질문 추천 상태(UP/NONE/DOWN)")
                                )

                        )));
    }

    @Test
    @DisplayName("질문의 추천 수정 Controller TEST - 변경사항 없을 경우")
    @WithMockUser
    void patchQuestionVote_NOCONTENT_case() throws Exception {
        // Given
        Long questionVoteId = 1L;
        QuestionVotePatchDto patchDto = TestStub.createQuestionVotePatchDto();
        QuestionVote testQuestionVote = TestStub.createTestQuestionVote();
        testQuestionVote.setVoteStatus(VoteStatus.NO_CONTENT);
        // When
        given(questionMapper.questionVotePatchDtoToEntity(any(QuestionVotePatchDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.updateVote(any(QuestionVote.class), anyLong()))
                .willReturn(testQuestionVote);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-vote/vote/{questionVoteId}", questionVoteId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(MockMvcRestDocumentation.document("patchNoContentQuestionVote",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("questionVoteId").description("질문 추천 식별자")
                        ),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("voteStatus").type(JsonFieldType.STRING).description("추천 상태 (UP/DOWN)")
                                )

                        )));
    }
}