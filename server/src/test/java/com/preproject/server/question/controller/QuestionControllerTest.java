package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.question.dto.QuestionPatchDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
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
import org.springframework.data.domain.Page;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(QuestionController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class QuestionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionMapper questionMapper;

    @Test
    @DisplayName("질문 단건 조회 Controller TEST")
    void getQuestion() throws Exception {
        // Given
        Long questionId = 1L;
        Question testQuestion = TestStub.createTestQuestion();
        QuestionResponseDto dto = TestStub.createQuestionResponseDto(testQuestion);
        // When
        given(questionService.get(anyLong())).willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(dto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/questions/{questionId}",questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(dto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(dto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(dto.getBody()))
                .andDo(MockMvcRestDocumentation.document("getQuestion",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.pathParameters(
                                parameterWithName("questionId").description("질문 식별자")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문의 Title"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("질문의 Body"),
                                        fieldWithPath("data.viewCounting").type(JsonFieldType.NUMBER).description("조회 수"),
                                        fieldWithPath("data.questionStatus").type(JsonFieldType.STRING).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.countingVote").type(JsonFieldType.NUMBER).description("질문의 추천 수"),
                                        fieldWithPath("data.answerCounting").type(JsonFieldType.NUMBER).description("질문의 답변 수"),
                                        fieldWithPath("data.questionVotes").type(JsonFieldType.ARRAY).description("질문의 추천 목록"),
                                        fieldWithPath("data.questionVotes[].questionVoteId").type(JsonFieldType.NUMBER).description("질문의 추천 식별자"),
                                        fieldWithPath("data.questionVotes[].userId").type(JsonFieldType.NUMBER).description("질문의 추천 회원 식별자"),
                                        fieldWithPath("data.questionVotes[].voteStatus").type(JsonFieldType.STRING).description("질문의 추천 상태"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("질문의 답변 목록"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("질문의 답변 식별자"),
                                        fieldWithPath("data.answers[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 회원 식별자"),
                                        fieldWithPath("data.answers[].displayName").type(JsonFieldType.STRING).description("질문의 답변 회원 닉네임"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("질문의 답변 내용"),
                                        fieldWithPath("data.answers[].check").type(JsonFieldType.BOOLEAN).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data.answers[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.answers[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answers[].answerComments").type(JsonFieldType.ARRAY).description("질문의 답변 코멘트 목록"),
                                        fieldWithPath("data.answers[].answerComments[].answerCommentId").type(JsonFieldType.NUMBER).description("질문의 답변 코멘트 식별자"),
                                        fieldWithPath("data.answers[].answerComments[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 코멘트 회원 식별자"),
                                        fieldWithPath("data.answers[].answerComments[].displayName").type(JsonFieldType.STRING).description("질문의 답변 코멘트 회원 닉네임"),
                                        fieldWithPath("data.answers[].answerComments[].comment").type(JsonFieldType.STRING).description("질문의 답변 코멘트 내용"),
                                        fieldWithPath("data.answers[].answerComments[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.answers[].answerComments[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answers[].answerVotes").type(JsonFieldType.ARRAY).description("질문의 답변 추천 목록"),
                                        fieldWithPath("data.answers[].answerVotes[].answerVoteId").type(JsonFieldType.NUMBER).description("질문의 답변 추천 식별자 "),
                                        fieldWithPath("data.answers[].answerVotes[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 추천 회원 식별자"),
                                        fieldWithPath("data.answers[].answerVotes[].voteStatus").type(JsonFieldType.STRING).description("질문의 답변 상태"),
                                        fieldWithPath("data.answers[].countingVote").type(JsonFieldType.NUMBER).description("질문의 답변 추천 수"),
                                        fieldWithPath("data.questionComments").type(JsonFieldType.ARRAY).description("질문의 코멘트 목록"),
                                        fieldWithPath("data.questionComments[].questionCommentId").type(JsonFieldType.NUMBER).description("질문의 코멘트 식별자"),
                                        fieldWithPath("data.questionComments[].userId").type(JsonFieldType.NUMBER).description("질문의 코멘트 회원 식별자"),
                                        fieldWithPath("data.questionComments[].displayName").type(JsonFieldType.STRING).description("질문의 코멘트 회원 닉네임"),
                                        fieldWithPath("data.questionComments[].comment").type(JsonFieldType.STRING).description("질문의 코멘트 내용"),
                                        fieldWithPath("data.questionComments[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.questionComments[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("질문이 소유하고 있는 태그 리스트"),
                                        fieldWithPath("data.tags[].tagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.tags[].tag").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("data.tags[].description").type(JsonFieldType.STRING).description("태그 설명"),
                                        fieldWithPath("data.tags[].createAt").type(JsonFieldType.STRING).description("생성 시각")

                                )
                        )
                ));
    }

    @Test
    @DisplayName("질문 생성 Controller TEST")
    @WithMockUser
    void postQuestion() throws Exception {
        // Given
        QuestionPostDto postDto = TestStub.createQuestionPostDto();
        Question testQuestion = TestStub.createTestQuestion();
        QuestionResponseDto responseDto = TestStub.createNewQuestionResponseDto(testQuestion);
        // When
        given(questionMapper.questionPostDtoToEntity(any(QuestionPostDto.class)))
                .willReturn(testQuestion);
        given(questionService.save(any(Question.class), anyString(), anyLong()))
                .willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders.post("/questions")
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.questionId").value(responseDto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()))
                .andDo(MockMvcRestDocumentation.document("postQuestion",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        HeaderDocumentation.requestHeaders(
                                headerWithName("Authorization").description("AccessToken"),
                                headerWithName("Refresh").description("RefreshToken")
                        ),
                        PayloadDocumentation.requestFields(
                                List.of(
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목"),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("질문 내용"),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("tags").type(JsonFieldType.STRING).description("질문 태그 리스트")
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문의 Title"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("질문의 Body"),
                                        fieldWithPath("data.viewCounting").type(JsonFieldType.NUMBER).description("조회 수"),
                                        fieldWithPath("data.questionStatus").type(JsonFieldType.STRING).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.countingVote").type(JsonFieldType.NUMBER).description("질문의 추천 수"),
                                        fieldWithPath("data.answerCounting").type(JsonFieldType.NUMBER).description("질문의 답변 수"),
                                        fieldWithPath("data.questionVotes").type(JsonFieldType.ARRAY).description("질문의 추천 목록"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("질문의 답변 목록"),
                                        fieldWithPath("data.questionComments").type(JsonFieldType.ARRAY).description("질문의 코멘트 목록"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("질문이 소유하고 있는 태그 리스트")

                                )
                        )
                ));
    }

    @Test
    @DisplayName("질문 수정 Controller TEST")
    @WithMockUser
    void patchQuestion() throws Exception {
        // Given
        Long questionId = 1L;
        QuestionPatchDto patchDto = TestStub.createQuestionPatchDto();
        Question testQuestion = TestStub.createTestQuestion();
        QuestionResponseDto responseDto = TestStub.createQuestionResponseDto(testQuestion);
        // When
        given(questionService.patch(anyLong(), any(Question.class), anyString(), anyLong()))
                .willReturn(testQuestion);
        given(questionMapper.questionPatchDtoToEntity(any(QuestionPatchDto.class)))
                .willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders.patch("/questions/{questionId}",questionId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(responseDto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()))
                .andDo(MockMvcRestDocumentation.document("patchQuestion",
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
                                        fieldWithPath("title").type(JsonFieldType.STRING).description("질문 제목").optional(),
                                        fieldWithPath("body").type(JsonFieldType.STRING).description("질문 내용").optional(),
                                        fieldWithPath("userId").type(JsonFieldType.NUMBER).description("회원 식별자").optional(),
                                        fieldWithPath("tags").type(JsonFieldType.STRING).description("질문 태그 리스트").optional()
                                )

                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data.userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data.title").type(JsonFieldType.STRING).description("질문의 Title"),
                                        fieldWithPath("data.body").type(JsonFieldType.STRING).description("질문의 Body"),
                                        fieldWithPath("data.viewCounting").type(JsonFieldType.NUMBER).description("조회 수"),
                                        fieldWithPath("data.questionStatus").type(JsonFieldType.STRING).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data.createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.countingVote").type(JsonFieldType.NUMBER).description("질문의 추천 수"),
                                        fieldWithPath("data.answerCounting").type(JsonFieldType.NUMBER).description("질문의 답변 수"),
                                        fieldWithPath("data.questionVotes").type(JsonFieldType.ARRAY).description("질문의 추천 목록"),
                                        fieldWithPath("data.questionVotes[].questionVoteId").type(JsonFieldType.NUMBER).description("질문의 추천 식별자"),
                                        fieldWithPath("data.questionVotes[].userId").type(JsonFieldType.NUMBER).description("질문의 추천 회원 식별자"),
                                        fieldWithPath("data.questionVotes[].voteStatus").type(JsonFieldType.STRING).description("질문의 추천 상태"),
                                        fieldWithPath("data.answers").type(JsonFieldType.ARRAY).description("질문의 답변 목록"),
                                        fieldWithPath("data.answers[].answerId").type(JsonFieldType.NUMBER).description("질문의 답변 식별자"),
                                        fieldWithPath("data.answers[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 회원 식별자"),
                                        fieldWithPath("data.answers[].displayName").type(JsonFieldType.STRING).description("질문의 답변 회원 닉네임"),
                                        fieldWithPath("data.answers[].body").type(JsonFieldType.STRING).description("질문의 답변 내용"),
                                        fieldWithPath("data.answers[].check").type(JsonFieldType.BOOLEAN).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data.answers[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.answers[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answers[].answerComments").type(JsonFieldType.ARRAY).description("질문의 답변 코멘트 목록"),
                                        fieldWithPath("data.answers[].answerComments[].answerCommentId").type(JsonFieldType.NUMBER).description("질문의 답변 코멘트 식별자"),
                                        fieldWithPath("data.answers[].answerComments[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 코멘트 회원 식별자"),
                                        fieldWithPath("data.answers[].answerComments[].displayName").type(JsonFieldType.STRING).description("질문의 답변 코멘트 회원 닉네임"),
                                        fieldWithPath("data.answers[].answerComments[].comment").type(JsonFieldType.STRING).description("질문의 답변 코멘트 내용"),
                                        fieldWithPath("data.answers[].answerComments[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.answers[].answerComments[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.answers[].answerVotes").type(JsonFieldType.ARRAY).description("질문의 답변 추천 목록"),
                                        fieldWithPath("data.answers[].answerVotes[].answerVoteId").type(JsonFieldType.NUMBER).description("질문의 답변 추천 식별자 "),
                                        fieldWithPath("data.answers[].answerVotes[].userId").type(JsonFieldType.NUMBER).description("질문의 답변 추천 회원 식별자"),
                                        fieldWithPath("data.answers[].answerVotes[].voteStatus").type(JsonFieldType.STRING).description("질문의 답변 상태"),
                                        fieldWithPath("data.answers[].countingVote").type(JsonFieldType.NUMBER).description("질문의 답변 추천 수"),
                                        fieldWithPath("data.questionComments").type(JsonFieldType.ARRAY).description("질문의 코멘트 목록"),
                                        fieldWithPath("data.questionComments[].questionCommentId").type(JsonFieldType.NUMBER).description("질문의 코멘트 식별자"),
                                        fieldWithPath("data.questionComments[].userId").type(JsonFieldType.NUMBER).description("질문의 코멘트 회원 식별자"),
                                        fieldWithPath("data.questionComments[].displayName").type(JsonFieldType.STRING).description("질문의 코멘트 회원 닉네임"),
                                        fieldWithPath("data.questionComments[].comment").type(JsonFieldType.STRING).description("질문의 코멘트 내용"),
                                        fieldWithPath("data.questionComments[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data.questionComments[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data.tags").type(JsonFieldType.ARRAY).description("질문이 소유하고 있는 태그 리스트"),
                                        fieldWithPath("data.tags[].tagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data.tags[].tag").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("data.tags[].description").type(JsonFieldType.STRING).description("태그 설명"),
                                        fieldWithPath("data.tags[].createAt").type(JsonFieldType.STRING).description("생성 시각")

                                )
                        )
                ));
    }

    @Test
    @DisplayName("질문 전체 조회 Controller TEST")
    void getQuestions() throws Exception {
        // Given
        Question testQuestion = TestStub.createTestQuestion();
        QuestionSimpleResponseDto dto =
                TestStub.createQuestionSimpleResponseDto(testQuestion);
        Page<Question> findQuestions =
                new PageImpl<>(List.of(testQuestion, testQuestion),
                        PageRequest.of(0, 10),
                        2);
        // When
        given(questionService.findAll(any(Pageable.class))).willReturn(findQuestions);
        given(questionMapper.questionListToSimpleResponseDtoList(anyList()))
                .willReturn(List.of(dto, dto));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/questions?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].questionId").value(dto.getQuestionId()))
                .andExpect(jsonPath("$.data[0].userId").value(dto.getUserId()))
                .andExpect(jsonPath("$.data[0].title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(dto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(MockMvcRestDocumentation.document("getQuestions",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("page").description("요청 페이지(0부터 1페이지)")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("질문의 Title"),
                                        fieldWithPath("data[].body").type(JsonFieldType.STRING).description("질문의 Body"),
                                        fieldWithPath("data[].questionStatus").type(JsonFieldType.STRING).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data[].countingVote").type(JsonFieldType.NUMBER).description("추천 수"),
                                        fieldWithPath("data[].viewCounting").type(JsonFieldType.NUMBER).description("조회 수"),
                                        fieldWithPath("data[].answerCounting").type(JsonFieldType.NUMBER).description("질문의 답변 수"),
                                        fieldWithPath("data[].tags").type(JsonFieldType.ARRAY).description("질문이 소유하고 있는 태그 리스트"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")
                                )
                        )
                ));
    }

    @Test
    @DisplayName("질문 삭제 Controller TEST")
    @WithMockUser
    void deleteQuestion() throws Exception {
        // Given
        Long questionId = 1L;
        // When
        doNothing().when(questionService).delete(anyLong());
        RequestBuilder result = RestDocumentationRequestBuilders.delete("/questions/{questionId}",questionId)
                .header("Authorization","AccessToken")
                .header("Refresh","RefreshToken")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent())
                .andDo(
                        MockMvcRestDocumentation.document("deleteQuestion",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                HeaderDocumentation.requestHeaders(
                                        headerWithName("Authorization").description("AccessToken"),
                                        headerWithName("Refresh").description("RefreshToken")
                                ),
                                RequestDocumentation.pathParameters(
                                        parameterWithName("questionId").description("질문 식별자")
                                )));
    }
}