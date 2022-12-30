package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.question.dto.QuestionPatchDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
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
import org.springframework.data.domain.Page;
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
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
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
        Question testQuestion = createTestQuestion();
        QuestionResponseDto dto = createQuestionResponseDto(testQuestion);
        // When
        given(questionService.get(anyLong())).willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(dto);
        RequestBuilder result = RestDocumentationRequestBuilders.get("/questions/"+questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(dto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(dto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(dto.getBody()));
    }

    @Test
    @DisplayName("질문 생성 Controller TEST")
    @WithMockUser
    void postQuestion() throws Exception {
        // Given
        QuestionPostDto postDto = createQuestionPostDto();
        Question testQuestion = createTestQuestion();
        QuestionResponseDto responseDto = createQuestionResponseDto(testQuestion);
        // When
        given(questionMapper.questionPostDtoToEntity(any(QuestionPostDto.class)))
                .willReturn(testQuestion);
        given(questionService.save(any(Question.class), anyString(), anyLong()))
                .willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders.post("/questions")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.questionId").value(responseDto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()));
    }

    @Test
    @DisplayName("질문 수정 Controller TEST")
    @WithMockUser
    void patchQuestion() throws Exception {
        // Given
        Long questionId = 1L;
        QuestionPatchDto patchDto = createQuestionPatchDto();
        Question testQuestion = createTestQuestion();
        QuestionResponseDto responseDto = createQuestionResponseDto(testQuestion);
        // When
        given(questionService.patch(anyLong(), any(Question.class), anyString(), anyLong()))
                .willReturn(testQuestion);
        given(questionMapper.questionPatchDtoToEntity(any(QuestionPatchDto.class)))
                .willReturn(testQuestion);
        given(questionMapper.QuestionEntityToResponseDto(any(Question.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders.patch("/questions/"+questionId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.questionId").value(responseDto.getQuestionId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()));
    }

    @Test
    @DisplayName("질문 전체 조회 Controller TEST")
    void getQuestions() throws Exception {
        // Given
        Question testQuestion = createTestQuestion();
        QuestionSimpleResponseDto dto =
                createQuestionSimpleResponseDto(testQuestion);
        Page<Question> findQuestions =
                new PageImpl<>(List.of(testQuestion, testQuestion),
                        PageRequest.of(0, 10),
                        2);
        // When
        given(questionService.findAll(any(Pageable.class))).willReturn(findQuestions);
        given(questionMapper.questionListToSimpleResponseDtoList(anyList()))
                .willReturn(List.of(dto, dto));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/questions")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].questionId").value(dto.getQuestionId()))
                .andExpect(jsonPath("$.data[0].userId").value(dto.getUserId()))
                .andExpect(jsonPath("$.data[0].title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(dto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));
    }

    @Test
    @DisplayName("질문 삭제 Controller TEST")
    @WithMockUser
    void deleteQuestion() throws Exception {
        // Given
        Long questionId = 1L;
        // When
        doNothing().when(questionService).delete(anyLong());
        RequestBuilder result = RestDocumentationRequestBuilders.delete("/questions/"+questionId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());
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

    private QuestionPostDto createQuestionPostDto() {
        QuestionPostDto dto = new QuestionPostDto();
        dto.setTitle("testTitle");
        dto.setBody("testBody");
        dto.setUserId(1L);
        dto.setTags("test,java");
        return dto;
    }
    private QuestionPatchDto createQuestionPatchDto() {
        QuestionPatchDto dto = new QuestionPatchDto();
        dto.setTitle("testTitle");
        dto.setBody("testBody");
        dto.setUserId(1L);
        dto.setTags("test,java");
        return dto;
    }

    private QuestionResponseDto createQuestionResponseDto(Question question) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setQuestionId(1L);
        dto.setUserId(question.getUser().getUserId());
        dto.setDisplayName(question.getUser().getDisplayName());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setViewCounting(1);
        dto.setQuestionStatus(question.getQuestionStatus().name());
        dto.setQuestionVotes(List.of());
        dto.setAnswers(List.of());
        dto.setQuestionComments(List.of());
        dto.setTags(List.of());
        return dto;
    }
    private QuestionSimpleResponseDto createQuestionSimpleResponseDto(Question question) {
        QuestionSimpleResponseDto dto = new QuestionSimpleResponseDto();
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        return dto;
    }

}