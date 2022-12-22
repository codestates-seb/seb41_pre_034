package com.preproject.server.utils;

import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.user.dto.UserResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StubDtoUtils {

    public UserResponseDto createUserResponseDto() {
        return new UserResponseDto(
                1L,
                "test@test.com",
                "testUser",
                true,
                UserStatus.ACTIVITY.toString(),
                LoginType.BASIC.toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(createQuestionResponseDto(),
                        createQuestionResponseDto()),
                List.of(createAnswerResponseDto(),
                        createAnswerResponseDto()),
                List.of(createTagResponseDto(),
                        createTagResponseDto()
                )
        );
    }

    public UserResponseDto createUserDto() {
        return new UserResponseDto(
                1L,
                "test@test.com",
                "testUser",
                true,
                UserStatus.ACTIVITY.toString(),
                LoginType.BASIC.toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public Page<UserResponseDto> createUserResponseDtoPage(Pageable pageable) {
        List<UserResponseDto> userResponseDto = List.of(
                createUserResponseDto(),
                createUserResponseDto(),
                createUserResponseDto());

        return new PageImpl<>(
                userResponseDto,
                pageable,
                userResponseDto.size()
        );
    }

    public Page<TagResponseDto> createTagResponseDtoPage(Pageable pageable) {
        List<TagResponseDto> tagResponseDto = List.of(
                createTagResponseDto(),
                createTagResponseDto(),
                createTagResponseDto(),
                createTagResponseDto(),
                createTagResponseDto(),
                createTagResponseDto()
        );
        return new PageImpl<>(
                tagResponseDto,
                pageable,
                tagResponseDto.size()
        );
    }

    public TagResponseDto createTagResponseDto() {
        return new TagResponseDto(
                1L,
                "java",
                "Hello World",
                LocalDateTime.now()
        );
    }

    public QuestionResponseDto createQuestionResponseDto() {
        return new QuestionResponseDto(
                1L,
                1L,
                "test",
                "Test Title",
                "Test Body",
                1,
                QuestionStatus.OPENED.toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(createQuestionVoteResponseDto(),
                        createQuestionVoteResponseDto(),
                        createQuestionVoteResponseDto()),
                3,
                List.of(createAnswerResponseDto()),
                1,
                List.of(createQuestionCommentResponseDto(),
                        createQuestionCommentResponseDto()),
                List.of(createTagResponseDto(),
                        createTagResponseDto()
                )
        );
    }

    public QuestionResponseDto createQuestionDto() {
        return new QuestionResponseDto(
                1L,
                1L,
                "test",
                "Test Title",
                "Test Body",
                0,
                QuestionStatus.OPENED.toString(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>(),
                0,
                new ArrayList<>(),
                0,
                new ArrayList<>(),
                new ArrayList<>()
        );
    }

    public Page<QuestionResponseDto> createQuestionResponseDtoPage(Pageable pageable) {
        List<QuestionResponseDto> questionResponseDto = List.of(
                createQuestionResponseDto(),
                createQuestionResponseDto(),
                createQuestionResponseDto()
        );
        return new PageImpl<>(
                questionResponseDto,
                pageable,
                questionResponseDto.size()
        );
    }

    public AnswerResponseDto createAnswerResponseDto() {
        return new AnswerResponseDto(
                1L,
                1L,
                "testUser",
                "Test Body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                List.of(createAnswerCommentResponseDto(),
                        createAnswerCommentResponseDto()),
                List.of(createAnswerVoteResponseDto(),
                        createAnswerVoteResponseDto(),
                        createAnswerVoteResponseDto()),
                3
        );
    }

    public QuestionVoteResponseDto createQuestionVoteResponseDto() {
        return new QuestionVoteResponseDto(
                1L,
                1L,
                VoteStatus.UP.toString()
        );
    }

    public AnswerVoteResponseDto createAnswerVoteResponseDto() {
        return new AnswerVoteResponseDto(
                1L,
                1L,
                VoteStatus.UP.toString()
        );
    }

    public QuestionCommentResponseDto createQuestionCommentResponseDto() {
        return new QuestionCommentResponseDto(
                1L,
                1L,
                "testUser",
                "Test Comment",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    public AnswerCommentResponseDto createAnswerCommentResponseDto() {
        return new AnswerCommentResponseDto(
                1L,
                1L,
                "testUser",
                "Test Comment",
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }



}
