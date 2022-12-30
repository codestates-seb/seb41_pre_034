package com.preproject.server.utils;

import com.preproject.server.answer.dto.*;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.*;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;

import java.time.LocalDateTime;
import java.util.List;

public class TestStub {

    public static User createTestUser() {
        User user = new User();
        user.setUserId(1L);
        user.setEmail("testaa@test.com");
        user.setPassword("1111!");
        user.setDisplayName("testUser");
        user.setEmailNotice(true);
        user.setLoginType(LoginType.BASIC);
        user.setUserStatus(UserStatus.ACTIVITY);
        user.setRoles(JwtAuthorityUtils.USER_ROLES_STRING_CALL);
        return user;
    }

    public static UserPostDto createUserPostDto() {
        return new UserPostDto(
                "testaa@test.com",
                "1111!",
                "testUser",
                true);
    }

    public static UserPatchDto createUserPatchDto() {
        UserPatchDto userPatchDto = new UserPatchDto();
        userPatchDto.setDisplayName("patchUser");
        return userPatchDto;
    }

    public static UserSimpleResponseDto createSimpleResponseDto(User user) {

        UserSimpleResponseDto userSimpleResponseDto = new UserSimpleResponseDto();

        userSimpleResponseDto.setUserId( user.getUserId() );
        userSimpleResponseDto.setEmail( user.getEmail() );
        userSimpleResponseDto.setDisplayName( user.getDisplayName() );
        userSimpleResponseDto.setEmailNotice( user.getEmailNotice() );
        if ( user.getUserStatus() != null ) {
            userSimpleResponseDto.setUserStatus( user.getUserStatus().name() );
        }
        if ( user.getLoginType() != null ) {
            userSimpleResponseDto.setLoginType( user.getLoginType().name() );
        }
        userSimpleResponseDto.setCreateAt(LocalDateTime.now());
        userSimpleResponseDto.setUpdateAt(LocalDateTime.now());
        return userSimpleResponseDto;
    }

    public static UserResponseDto createUserResponseDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setUserId(user.getUserId());
        dto.setEmail(user.getEmail());
        dto.setDisplayName(user.getDisplayName());
        dto.setEmailNotice(user.getEmailNotice());
        dto.setUserStatus(user.getUserStatus().name());
        dto.setLoginType(user.getLoginType().name());
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setQuestions(List.of(
                createQuestionResponseDto(createTestQuestion())
        ));
        dto.setAnswers(List.of(
                createAnswerResponseDto()
        ));
        dto.setTags(List.of(
                createTagResponseDto()
        ));
        return dto;
    }


    public static Question createTestQuestion() {
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

    public static QuestionSimpleDto createQuestSimpleDto(Question question) {
        QuestionSimpleDto dto = new QuestionSimpleDto();
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        return dto;
    }

    public static QuestionPostDto createQuestionPostDto() {
        QuestionPostDto dto = new QuestionPostDto();
        dto.setTitle("testTitle");
        dto.setBody("testBody");
        dto.setUserId(1L);
        dto.setTags("test,java");
        return dto;
    }
    public static QuestionPatchDto createQuestionPatchDto() {
        QuestionPatchDto dto = new QuestionPatchDto();
        dto.setTitle("testTitle");
        dto.setBody("testBody");
        dto.setUserId(1L);
        dto.setTags("test,java");
        return dto;
    }

    public static QuestionResponseDto createNewQuestionResponseDto(Question question) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setQuestionId(1L);
        dto.setUserId(question.getUser().getUserId());
        dto.setDisplayName(question.getUser().getDisplayName());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setViewCounting(1);
        dto.setQuestionStatus(question.getQuestionStatus().name());
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setQuestionVotes(List.of());
        dto.setAnswers(List.of());
        dto.setQuestionComments(List.of());
        dto.setTags(List.of());
        return dto;
    }

    public static QuestionResponseDto createQuestionResponseDto(Question question) {
        QuestionResponseDto dto = new QuestionResponseDto();
        dto.setQuestionId(1L);
        dto.setUserId(question.getUser().getUserId());
        dto.setDisplayName(question.getUser().getDisplayName());
        dto.setTitle(question.getTitle());
        dto.setBody(question.getBody());
        dto.setViewCounting(1);
        dto.setQuestionStatus(question.getQuestionStatus().name());
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setQuestionVotes(List.of(
                createQuestionVoteResponseDto(),createQuestionVoteResponseDto()
        ));
        dto.setAnswers(List.of(
                createAnswerResponseDto(),createAnswerResponseDto())
        );
        dto.setQuestionComments(List.of(
                createQuestionCommentResponseDto(),createQuestionCommentResponseDto()
        ));
        dto.setTags(List.of(
                createTagResponseDto(),createTagResponseDto()
        ));
        return dto;
    }

    public static QuestionSimpleResponseDto createQuestionSimpleResponseDto(Question question) {
        QuestionSimpleResponseDto dto = new QuestionSimpleResponseDto();
        dto.setQuestionId(1L);
        dto.setQuestionStatus("OPENED");
        dto.setDisplayName("testUesr");
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        dto.setTags(List.of("java","test"));
        return dto;
    }
    public static QuestionComment createTestQuestionComment() {
        QuestionComment entity = new QuestionComment();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        entity.setComment("Test Comment");
        entity.setQuestion(createTestQuestion());
        entity.setUser(user);
        return entity;
    }

    public static QuestionCommentPostDto createQuestionCommentPostDto() {
        QuestionCommentPostDto dto = new QuestionCommentPostDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    public static QuestionCommentPatchDto createQuestionCommentPatchDto() {
        QuestionCommentPatchDto dto = new QuestionCommentPatchDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    public static QuestionCommentResponseDto createQuestionCommentResponseDto() {
        QuestionCommentResponseDto dto = new QuestionCommentResponseDto();
        dto.setQuestionCommentId(1L);
        dto.setUserId(1L);
        dto.setDisplayName("Test User");
        dto.setComment("Test Comment");
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        return dto;
    }

    public static QuestionVoteResponseDto createQuestionVoteResponseDto() {
        QuestionVoteResponseDto dto = new QuestionVoteResponseDto();
        dto.setUserId(1L);
        dto.setVoteStatus("NONE");
        dto.setQuestionVoteId(1L);
        return dto;
    }

    public static QuestionVote createTestQuestionVote() {
        QuestionVote vote = new QuestionVote();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        vote.setVoteStatus(VoteStatus.UP);
        vote.setQuestion(createTestQuestion());
        vote.setUser(user);
        return vote;
    }

    public static QuestionVotePostDto createQuestionVotePostDto() {
        QuestionVotePostDto dto = new QuestionVotePostDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }
    public static QuestionVotePatchDto createQuestionVotePatchDto() {
        QuestionVotePatchDto dto = new QuestionVotePatchDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    public static Answer createTestAnswer() {
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

    public static AnswerPostDto createPostDto() {
        AnswerPostDto dto = new AnswerPostDto();
        dto.setUserId(1L);
        dto.setQuestionId(1L);
        dto.setBody("Test Answer");
        return dto;
    }

    public static AnswerPatchDto createPatchDto() {
        AnswerPatchDto dto = new AnswerPatchDto();
        dto.setUserId(1L);
        dto.setQuestionId(1L);
        dto.setCheck(true);
        dto.setBody("Test Answer");
        return dto;
    }

    public static AnswerResponseDto createAnswerResponseDto() {
        AnswerResponseDto dto = new AnswerResponseDto();
        dto.setAnswerId(1L);
        dto.setUserId(1L);
        dto.setDisplayName("testUser");
        dto.setCheck(false);
        dto.setBody("Test Body");
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setCountingVote(1);
        dto.setAnswerComments(
                List.of(createAnswerCommentResponseDto(),createAnswerCommentResponseDto())
        );
        dto.setAnswerVotes(
                List.of(createAnswerVoteResponseDto(),createAnswerVoteResponseDto())
        );
        return dto;
    }

    public static AnswerResponseDto createNewAnswerResponseDto(Answer answer) {
        AnswerResponseDto dto = new AnswerResponseDto();
        dto.setAnswerId(1L);
        dto.setUserId(answer.getUser().getUserId());
        dto.setDisplayName(answer.getUser().getDisplayName());
        dto.setCheck(answer.getCheck());
        dto.setBody(answer.getBody());
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setCountingVote(0);
        dto.setAnswerVotes(List.of());
        dto.setAnswerComments(List.of());
        return dto;
    }
    public static AnswerVotePostDto createAnswerVotePostDto() {
        AnswerVotePostDto dto = new AnswerVotePostDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    public static AnswerVotePatchDto createAnswerVotePatchDto() {
        AnswerVotePatchDto dto = new AnswerVotePatchDto();
        dto.setUserId(1L);
        dto.setVoteStatus("up");
        return dto;
    }

    public static AnswerVoteResponseDto createAnswerVoteResponseDto() {
        AnswerVoteResponseDto dto = new AnswerVoteResponseDto();
        dto.setUserId(1L);
        dto.setVoteStatus("UP");
        dto.setAnswerVoteId(1L);
        return dto;
    }

    public static AnswerVote createTestAnswerVote() {
        AnswerVote vote = new AnswerVote();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        vote.setVoteStatus(VoteStatus.UP);
        vote.setAnswer(createTestAnswer());
        vote.setUser(user);
        return vote;
    }


    public static AnswerComment createTestAnswerComment() {
        AnswerComment entity = new AnswerComment();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        entity.setComment("Test Comment");
        entity.setAnswer(createTestAnswer());
        entity.setUser(user);
        return entity;
    }

    public static AnswerCommentResponseDto createAnswerCommentResponseDto() {
        AnswerCommentResponseDto dto = new AnswerCommentResponseDto();
        dto.setAnswerCommentId(1L);
        dto.setUserId(1L);
        dto.setDisplayName("testUser");
        dto.setComment("Test Comment");
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        return dto;

    }

    public static AnswerCommentPostDto createAnswerCommentPostDto() {
        AnswerCommentPostDto dto = new AnswerCommentPostDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    public static AnswerCommentPatchDto createAnswerCommentPatchDto() {
        AnswerCommentPatchDto dto = new AnswerCommentPatchDto();
        dto.setComment("Test Comment");
        dto.setUserId(1L);
        return dto;
    }

    public static Tag createTestTag() {
        Tag tag = new Tag();
        tag.setTag("test");
        tag.setDescription("test");
        return tag;
    }

    public static QuestionTag createTestQuestionTag() {
        QuestionTag questionTag = new QuestionTag();
        Tag testTag = createTestTag();
        Question testQuestion = createTestQuestion();
        questionTag.setTag(testTag);
        questionTag.setQuestion(testQuestion);
        return questionTag;
    }

    public static TagResponseDto createTagResponseDto() {
        TagResponseDto dto = new TagResponseDto();
        dto.setTagId(1L);
        dto.setCreateAt(LocalDateTime.now());
        dto.setTag("java");
        dto.setDescription("test");
        return dto;
    }

}
