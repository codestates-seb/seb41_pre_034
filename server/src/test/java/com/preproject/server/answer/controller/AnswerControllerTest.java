package com.preproject.server.answer.controller;

import com.google.gson.Gson;
import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerService;
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
        AnswerPostDto postDto = createPostDto();
        Answer testAnswer = createTestAnswer();
        AnswerResponseDto responseDto = createResponseDto(testAnswer);
        // When
        given(answerService.createAnswer(any(Answer.class), anyLong(), anyLong()))
                .willReturn(testAnswer);
        given(answerMapper.answerPostDtoToEntity(any(AnswerPostDto.class)))
                .willReturn(testAnswer);
        given(answerMapper.entityToResponseDto(any(Answer.class))).willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders.post("/answers")
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.answerId").value(responseDto.getAnswerId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()));
    }

    @Test
    @DisplayName("답변 수정 Controller TEST")
    @WithMockUser
    void patchAnswer() throws Exception {
        // Given
        Long answerId = 1L;
        AnswerPatchDto patchDto = createPatchDto();
        Answer testAnswer = createTestAnswer();
        AnswerResponseDto responseDto = createResponseDto(testAnswer);
        // When
        given(answerMapper.answerPatchDtoToEntity(any(AnswerPatchDto.class)))
                .willReturn(testAnswer);
        given(answerService.updateAnswer(any(Answer.class))).willReturn(testAnswer);
        given(answerMapper.entityToResponseDto(any(Answer.class))).willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders.patch("/answers/"+answerId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.answerId").value(responseDto.getAnswerId()))
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.displayName").value(responseDto.getDisplayName()))
                .andExpect(jsonPath("$.data.body").value(responseDto.getBody()));
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
        RequestBuilder result = RestDocumentationRequestBuilders.delete("/answers/"+answerId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());
    }

    private AnswerPostDto createPostDto() {
        AnswerPostDto dto = new AnswerPostDto();
        dto.setUserId(1L);
        dto.setQuestionId(1L);
        dto.setBody("Test Answer");
        return dto;
    }

    private AnswerPatchDto createPatchDto() {
        AnswerPatchDto dto = new AnswerPatchDto();
        dto.setUserId(1L);
        dto.setQuestionId(1L);
        dto.setCheck(true);
        dto.setBody("Test Answer");
        return dto;
    }
    private AnswerResponseDto createResponseDto(Answer answer) {
        AnswerResponseDto dto = new AnswerResponseDto();
        dto.setUserId(answer.getUser().getUserId());
        dto.setDisplayName(answer.getUser().getDisplayName());
        dto.setAnswerId(1L);
        dto.setCheck(answer.getCheck());
        dto.setBody(answer.getBody());
        return dto;
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