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
import com.preproject.server.user.dto.UserSimpleResponseDto;
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


    /* User 응답 데이터 */
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

    /* User 전체 조회시 Response
    *  전체 조회시 User의 작성 질문과 답변까지 응답 되어 불필요한 데이터 제거 */
    public UserSimpleResponseDto createUsersResponseDto() {
        return new UserSimpleResponseDto(
                1L,
                "test@test.com",
                "testUser",
                true,
                UserStatus.ACTIVITY.toString(),
                LoginType.BASIC.toString(),
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    /* User create 응답 데이터 */
    public UserSimpleResponseDto createUserDto() {
        return new UserSimpleResponseDto(
                1L,
                "test@test.com",
                "testUser",
                true,
                UserStatus.ACTIVITY.toString(),
                LoginType.BASIC.toString(),
                LocalDateTime.now(),
                LocalDateTime.now()
        );
    }

    /* 전체 유저 조회 응답 데이터*/
    public Page<UserSimpleResponseDto> createUserResponseDtoPage(Pageable pageable) {
        List<UserSimpleResponseDto> userResponseDto = List.of(
                createUsersResponseDto(),
                createUsersResponseDto(),
                createUsersResponseDto());

        return new PageImpl<>(
                userResponseDto,
                pageable,
                userResponseDto.size()
        );
    }

    /* 전체 테그 조회 응답 데이터*/
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

    /* 태그 생성 응답 데이터 */
    public TagResponseDto createTagResponseDto() {
        return new TagResponseDto(
                1L,
                "java",
                "Hello World",
                LocalDateTime.now()
        );
    }

    /* 질문 조회 응답 데이터 */
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

    /* 질문 생성 응답 데이터 */
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

    /* 질문 전체 조회 응답 데이터 */
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

    /* 답변 조회 응답 데이터 */
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


    /* 답변 생성 응답 데이터 */
    public AnswerResponseDto createAnswerDto() {
        return new AnswerResponseDto(
                1L,
                1L,
                "testUser",
                "Test Body",
                LocalDateTime.now(),
                LocalDateTime.now(),
                new ArrayList<>(),
                new ArrayList<>(),
                0
        );
    }


    /* 질문 추천 응답 데이터 */
    public QuestionVoteResponseDto createQuestionVoteResponseDto() {
        return new QuestionVoteResponseDto(
                1L,
                1L,
                VoteStatus.UP.toString()
        );
    }


    /* 답변 추천 응답 데이터 */
    public AnswerVoteResponseDto createAnswerVoteResponseDto() {
        return new AnswerVoteResponseDto(
                1L,
                1L,
                VoteStatus.UP.toString()
        );
    }


    /* 질문 코멘트 응답 데이터 */
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


    /* 답변 코멘트 응답 데이터 */
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
