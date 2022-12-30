package com.preproject.server.question.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionVoteService;
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
        QuestionVotePostDto postDto = createPostDto();
        QuestionVote testQuestionVote = createTestQuestionVote();
        QuestionVoteResponseDto responseDto = createResponseDto(testQuestionVote);
        // When
        given(questionMapper.questionVotePostDtoToEntity(any(QuestionVotePostDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.createVote(any(QuestionVote.class), anyLong(), anyLong()))
                .willReturn(testQuestionVote);
        given(questionMapper.questionVoteToQuestionVoteResponseDto(any(QuestionVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(postDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .post("/question-vote/"+ questionId)
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
                .andExpect(jsonPath("$.data.questionVoteId").value(responseDto.getQuestionVoteId()));
    }

    @Test
    @DisplayName("질문의 추천 수정 Controller TEST - 변경사항 있을 경우")
    @WithMockUser
    void patchQuestionVote_OK_case() throws Exception {
        // Given
        Long questionVoteId = 1L;
        QuestionVotePatchDto patchDto = createPatchDto();
        QuestionVote testQuestionVote = createTestQuestionVote();
        QuestionVoteResponseDto responseDto = createResponseDto(testQuestionVote);
        // When
        given(questionMapper.questionVotePatchDtoToEntity(any(QuestionVotePatchDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.updateVote(any(QuestionVote.class), anyLong()))
                .willReturn(testQuestionVote);
        given(questionMapper.questionVoteToQuestionVoteResponseDto(any(QuestionVote.class)))
                .willReturn(responseDto);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-vote/vote/"+ questionVoteId)
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
                .andExpect(jsonPath("$.data.questionVoteId").value(responseDto.getQuestionVoteId()));
    }

    @Test
    @DisplayName("질문의 추천 수정 Controller TEST - 변경사항 없을 경우")
    @WithMockUser
    void patchQuestionVote_NOCONTENT_case() throws Exception {
        // Given
        Long questionVoteId = 1L;
        QuestionVotePatchDto patchDto = createPatchDto();
        QuestionVote testQuestionVote = createTestQuestionVote();
        testQuestionVote.setVoteStatus(VoteStatus.NO_CONTENT);
        // When
        given(questionMapper.questionVotePatchDtoToEntity(any(QuestionVotePatchDto.class)))
                .willReturn(testQuestionVote);
        given(questionVoteService.updateVote(any(QuestionVote.class), anyLong()))
                .willReturn(testQuestionVote);
        String content = gson.toJson(patchDto);
        RequestBuilder result = RestDocumentationRequestBuilders
                .patch("/question-vote/vote/"+ questionVoteId)
                .content(content)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isNoContent());
    }

    private QuestionVotePostDto createPostDto() {
        QuestionVotePostDto dto = new QuestionVotePostDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }
    private QuestionVotePatchDto createPatchDto() {
        QuestionVotePatchDto dto = new QuestionVotePatchDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    private QuestionVoteResponseDto createResponseDto(QuestionVote questionVote) {
        QuestionVoteResponseDto dto = new QuestionVoteResponseDto();
        dto.setUserId(questionVote.getUser().getUserId());
        dto.setVoteStatus(questionVote.getVoteStatus().name());
        dto.setQuestionVoteId(1L);
        return dto;
    }

    private QuestionVote createTestQuestionVote() {
        QuestionVote vote = new QuestionVote();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        vote.setVoteStatus(VoteStatus.UP);
        vote.setQuestion(createTestQuestion());
        vote.setUser(user);
        return vote;
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