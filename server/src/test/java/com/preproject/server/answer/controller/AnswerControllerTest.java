package com.preproject.server.answer.controller;

import com.google.gson.Gson;
import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerService;
import com.preproject.server.config.auth.SecurityConfig;
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


@WebMvcTest(AnswerController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class AnswerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerService answerService;

    @MockBean
    private AnswerMapper answerMapper;

    @Test
    @DisplayName("답변 생성 Controller TEST")
    @WithMockUser
    void postAnswer() throws Exception {
        // Given
        AnswerPostDto postDto = TestStub.createPostDto();
        Answer testAnswer = TestStub.createTestAnswer();
        AnswerResponseDto responseDto = TestStub.createNewAnswerResponseDto(testAnswer);
        // When
        given(answerService.createAnswer(any(Answer.class), anyLong(), anyLong()))
                .willReturn(testAnswer);
        given(answerMapper.answerPostDtoToEntity(any(AnswerPostDto.class)))
                .willReturn(testAnswer);
        given(answerMapper.entityToResponseDto(any(Answer.class))).willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders.post("/answers")
                .header("Authorization", "AccessToken")
                .header("Refresh", "RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.answerId").value(responseDto.getAnswerId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()))
                .andDo(MockMvcRestDocumentation.document("postAnswer",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("답변하고자 하는 질문 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("data.check").type(JsonFieldType.BOOLEAN).description("채택 여부"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answerComments").type(JsonFieldType.ARRAY).description("답변 코멘트 목록"),
                                        fieldWithPath("data.answerVotes").type(JsonFieldType.ARRAY).description("답변 추천 목록"),
                                        fieldWithPath("data.countingVote").type(JsonFieldType.NUMBER).description("답변 추천 수")
                                ))));
    }

    @Test
    @DisplayName("답변 수정 Controller TEST")
    @WithMockUser
    void patchAnswer() throws Exception {
        // Given
        Long answerId = 1L;
        AnswerPatchDto patchDto = TestStub.createPatchDto();
        Answer testAnswer = TestStub.createTestAnswer();
        AnswerResponseDto responseDto = TestStub.createAnswerResponseDto();
        // When
        given(answerMapper.answerPatchDtoToEntity(any(AnswerPatchDto.class)))
                .willReturn(testAnswer);
        given(answerService.updateAnswer(any(Answer.class))).willReturn(testAnswer);
        given(answerMapper.entityToResponseDto(any(Answer.class))).willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/answers/{answerId}", answerId)
                .header("Authorization", "AccessToken")
                .header("Refresh", "RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(responseDto.getAnswerId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()))
                .andDo(MockMvcRestDocumentation.document("patchAnswer",
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
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("questionId").type(JsonFieldType.NUMBER).description("답변하고자 하는 질문 식별자"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("check").type(JsonFieldType.BOOLEAN).description("답변 채택 여부")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.answerId").type(JsonFieldType.NUMBER).description("답변 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("회원 닉네임"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("답변 내용"),
                                        fieldWithPath("data.check").type(JsonFieldType.BOOLEAN).description("채택 여부"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answerComments").type(JsonFieldType.ARRAY).description("답변 코멘트 목록"),
                                        fieldWithPath("data.answerComments[].answerCommentId").type(JsonFieldType.NUMBER).description("답변 코멘트 식별자"),
                                        fieldWithPath("data.answerComments[].userId").type(JsonFieldType.NUMBER).description("답변 코멘트 회원 식별자"),
                                        fieldWithPath("data.answerComments[].displayName").type(JsonFieldType.STRING).description("답변 코멘트 회원 닉네임"),
                                        fieldWithPath("data.answerComments[].comment").type(JsonFieldType.STRING).description("답변 코멘트 내용"),
                                        fieldWithPath("data.answerComments[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.answerComments[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answerVotes").type(JsonFieldType.ARRAY).description("답변 추천 목록"),
                                        fieldWithPath("data.answerVotes[].answerVoteId").type(JsonFieldType.NUMBER).description("답변 추천 식별자"),
                                        fieldWithPath("data.answerVotes[].userId").type(JsonFieldType.NUMBER).description("답변 추천 회원 식별자"),
                                        fieldWithPath("data.answerVotes[].voteStatus").type(JsonFieldType.STRING).description("답변 추천 상태"),
                                        fieldWithPath("data.countingVote").type(JsonFieldType.NUMBER).description("답변 추천 수")
                                ))));
    }

    @Test
    @DisplayName("답변 삭제 Controller TEST")
    @WithMockUser
    void deleteTest() throws Exception {
        // Given
        Long answerId = 1L;
        // When
        doNothing().when(answerService).deleteAnswer(anyLong());
        // Then
        RequestBuilder result = RestDocumentationRequestBuilders
                .delete("/answers/{answerId}", answerId)
                .header("Authorization", "AccessToken")
                .header("Refresh", "RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteAnswer",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("answerId").description("답변 식별자")
                                )));
    }
}