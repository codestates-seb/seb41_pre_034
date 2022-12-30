package com.preproject.server.answer.controller;

import com.google.gson.Gson;
import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerVoteService;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.constant.VoteStatus;
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
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AnswerVoteController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class AnswerVoteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private AnswerMapper answerMapper;

    @MockBean
    private AnswerVoteService answerVoteService;

    @Test
    @DisplayName("답변의 추천 생성 Controller TEST")
    @WithMockUser
    void postVote() throws Exception {
        // Given
        Long answerId = 1L;
        AnswerVotePostDto postDto = createPostDto();
        AnswerVote testAnswerVote = createTestAnswerVote();
        AnswerVoteResponseDto responseDto = createResponseDto(testAnswerVote);
        // When
        given(answerVoteService.createVote(any(AnswerVote.class), anyLong(), anyLong()))
                .willReturn(testAnswerVote);
        given(answerMapper.answerVotePostDtoToEntity(any(AnswerVotePostDto.class)))
                .willReturn(testAnswerVote);
        given(answerMapper.answerVoteToAnswerVoteResponseDto(any(AnswerVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/answer-vote/"+ answerId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.voteStatus").value(responseDto.getVoteStatus()))
                .andExpect(jsonPath("$.data.answerVoteId").value(responseDto.getAnswerVoteId()));
    }

    @Test
    @DisplayName("답변의 추천 수정 Controller TEST - 변경사항 있을 경우")
    @WithMockUser
    void patchVote_OK_case() throws Exception {
        // Given
        Long answerVoteId = 1L;
        AnswerVotePatchDto patchDto = createPatchDto();
        AnswerVote testAnswerVote = createTestAnswerVote();
        AnswerVoteResponseDto responseDto = createResponseDto(testAnswerVote);
        // When
        given(answerMapper.answerVotePatchDtoToEntity(any(AnswerVotePatchDto.class)))
                .willReturn(testAnswerVote);
        given(answerVoteService.updateVote(any(AnswerVote.class), anyLong()))
                .willReturn(testAnswerVote);
        given(answerMapper.answerVoteToAnswerVoteResponseDto(any(AnswerVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/answer-vote/vote/"+ answerVoteId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data.voteStatus").value(responseDto.getVoteStatus()))
                .andExpect(jsonPath("$.data.answerVoteId").value(responseDto.getAnswerVoteId()));
    }

    @Test
    @DisplayName("답변의 추천 수정 Controller TEST - 변경사항 없을 경우")
    @WithMockUser
    void patchVote_NOCONTENT_case() throws Exception {
        // Given
        Long answerVoteId = 1L;
        AnswerVotePatchDto patchDto = createPatchDto();
        AnswerVote testAnswerVote = createTestAnswerVote();
        testAnswerVote.setVoteStatus(VoteStatus.NO_CONTENT);
        // When
        given(answerMapper.answerVotePatchDtoToEntity(any(AnswerVotePatchDto.class)))
                .willReturn(testAnswerVote);
        given(answerVoteService.updateVote(any(AnswerVote.class), anyLong()))
                .willReturn(testAnswerVote);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/answer-vote/vote/"+ answerVoteId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());
    }

    private AnswerVotePostDto createPostDto() {
        AnswerVotePostDto dto = new AnswerVotePostDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    private AnswerVotePatchDto createPatchDto() {
        AnswerVotePatchDto dto = new AnswerVotePatchDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    private AnswerVoteResponseDto createResponseDto(AnswerVote answerVote) {
        AnswerVoteResponseDto dto = new AnswerVoteResponseDto();
        dto.setUserId(answerVote.getUser().getUserId());
        dto.setVoteStatus(answerVote.getVoteStatus().name());
        dto.setAnswerVoteId(1L);
        return dto;
    }

    private AnswerVote createTestAnswerVote() {
        AnswerVote vote = new AnswerVote();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        vote.setVoteStatus(VoteStatus.UP);
        vote.setAnswer(createTestAnswer());
        vote.setUser(user);
        return vote;
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