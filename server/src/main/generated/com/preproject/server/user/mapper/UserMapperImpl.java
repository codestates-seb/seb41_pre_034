package com.preproject.server.user.mapper;

import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-24T00:04:54+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User UserPostDtoToEntity(UserPostDto userPostDto) {
        if ( userPostDto == null ) {
            return null;
        }

        User user = new User();

        user.setEmail( userPostDto.getEmail() );
        user.setPassword( userPostDto.getPassword() );
        user.setDisplayName( userPostDto.getDisplayName() );
        user.setEmailNotice( userPostDto.getEmailNotice() );

        return user;
    }

    @Override
    public User UserPatchDtoToEntity(UserPatchDto userPatchDto) {
        if ( userPatchDto == null ) {
            return null;
        }

        User user = new User();

        user.setUserId( userPatchDto.getUserId() );
        user.setPassword( userPatchDto.getPassword() );
        user.setDisplayName( userPatchDto.getDisplayName() );

        return user;
    }

    @Override
    public UserResponseDto userEntityToResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUserId( user.getUserId() );
        userResponseDto.setEmail( user.getEmail() );
        userResponseDto.setDisplayName( user.getDisplayName() );
        userResponseDto.setEmailNotice( user.getEmailNotice() );
        if ( user.getUserStatus() != null ) {
            userResponseDto.setUserStatus( user.getUserStatus().name() );
        }
        if ( user.getLoginType() != null ) {
            userResponseDto.setLoginType( user.getLoginType().name() );
        }
        userResponseDto.setCreateAt( user.getCreateAt() );
        userResponseDto.setUpdateAt( user.getUpdateAt() );
        userResponseDto.setQuestions( questionListToQuestionResponseDtoList( user.getQuestions() ) );
        userResponseDto.setAnswers( answerListToAnswerResponseDtoList( user.getAnswers() ) );

        return userResponseDto;
    }

    @Override
    public UserSimpleResponseDto userEntityToSimpleResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

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
        userSimpleResponseDto.setCreateAt( user.getCreateAt() );
        userSimpleResponseDto.setUpdateAt( user.getUpdateAt() );

        return userSimpleResponseDto;
    }

    @Override
    public List<UserSimpleResponseDto> UserListToResponseDtoList(List<User> userList) {
        if ( userList == null ) {
            return null;
        }

        List<UserSimpleResponseDto> list = new ArrayList<UserSimpleResponseDto>( userList.size() );
        for ( User user : userList ) {
            list.add( userEntityToSimpleResponseDto( user ) );
        }

        return list;
    }

    protected QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote) {
        if ( questionVote == null ) {
            return null;
        }

        QuestionVoteResponseDto questionVoteResponseDto = new QuestionVoteResponseDto();

        questionVoteResponseDto.setQuestionVoteId( questionVote.getQuestionVoteId() );
        if ( questionVote.getVoteStatus() != null ) {
            questionVoteResponseDto.setVoteStatus( questionVote.getVoteStatus().name() );
        }

        return questionVoteResponseDto;
    }

    protected List<QuestionVoteResponseDto> questionVoteListToQuestionVoteResponseDtoList(List<QuestionVote> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionVoteResponseDto> list1 = new ArrayList<QuestionVoteResponseDto>( list.size() );
        for ( QuestionVote questionVote : list ) {
            list1.add( questionVoteToQuestionVoteResponseDto( questionVote ) );
        }

        return list1;
    }

    protected AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if ( answerComment == null ) {
            return null;
        }

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto();

        answerCommentResponseDto.setAnswerCommentId( answerComment.getAnswerCommentId() );
        answerCommentResponseDto.setComment( answerComment.getComment() );
        answerCommentResponseDto.setCreateAt( answerComment.getCreateAt() );
        answerCommentResponseDto.setUpdateAt( answerComment.getUpdateAt() );

        return answerCommentResponseDto;
    }

    protected List<AnswerCommentResponseDto> answerCommentListToAnswerCommentResponseDtoList(List<AnswerComment> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerCommentResponseDto> list1 = new ArrayList<AnswerCommentResponseDto>( list.size() );
        for ( AnswerComment answerComment : list ) {
            list1.add( answerCommentToAnswerCommentResponseDto( answerComment ) );
        }

        return list1;
    }

    protected AnswerVoteResponseDto answerVoteToAnswerVoteResponseDto(AnswerVote answerVote) {
        if ( answerVote == null ) {
            return null;
        }

        AnswerVoteResponseDto answerVoteResponseDto = new AnswerVoteResponseDto();

        answerVoteResponseDto.setAnswerVoteId( answerVote.getAnswerVoteId() );
        if ( answerVote.getVoteStatus() != null ) {
            answerVoteResponseDto.setVoteStatus( answerVote.getVoteStatus().name() );
        }

        return answerVoteResponseDto;
    }

    protected List<AnswerVoteResponseDto> answerVoteListToAnswerVoteResponseDtoList(List<AnswerVote> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerVoteResponseDto> list1 = new ArrayList<AnswerVoteResponseDto>( list.size() );
        for ( AnswerVote answerVote : list ) {
            list1.add( answerVoteToAnswerVoteResponseDto( answerVote ) );
        }

        return list1;
    }

    protected AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setAnswerId( answer.getAnswerId() );
        answerResponseDto.setBody( answer.getBody() );
        answerResponseDto.setCheck( answer.getCheck() );
        answerResponseDto.setCreateAt( answer.getCreateAt() );
        answerResponseDto.setUpdateAt( answer.getUpdateAt() );
        answerResponseDto.setAnswerComments( answerCommentListToAnswerCommentResponseDtoList( answer.getAnswerComments() ) );
        answerResponseDto.setAnswerVotes( answerVoteListToAnswerVoteResponseDtoList( answer.getAnswerVotes() ) );

        return answerResponseDto;
    }

    protected List<AnswerResponseDto> answerListToAnswerResponseDtoList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerResponseDto> list1 = new ArrayList<AnswerResponseDto>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToAnswerResponseDto( answer ) );
        }

        return list1;
    }

    protected QuestionCommentResponseDto questionCommentToQuestionCommentResponseDto(QuestionComment questionComment) {
        if ( questionComment == null ) {
            return null;
        }

        QuestionCommentResponseDto questionCommentResponseDto = new QuestionCommentResponseDto();

        questionCommentResponseDto.setQuestionCommentId( questionComment.getQuestionCommentId() );
        questionCommentResponseDto.setComment( questionComment.getComment() );
        questionCommentResponseDto.setCreateAt( questionComment.getCreateAt() );
        questionCommentResponseDto.setUpdateAt( questionComment.getUpdateAt() );

        return questionCommentResponseDto;
    }

    protected List<QuestionCommentResponseDto> questionCommentListToQuestionCommentResponseDtoList(List<QuestionComment> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionCommentResponseDto> list1 = new ArrayList<QuestionCommentResponseDto>( list.size() );
        for ( QuestionComment questionComment : list ) {
            list1.add( questionCommentToQuestionCommentResponseDto( questionComment ) );
        }

        return list1;
    }

    protected QuestionResponseDto questionToQuestionResponseDto(Question question) {
        if ( question == null ) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setQuestionId( question.getQuestionId() );
        questionResponseDto.setTitle( question.getTitle() );
        questionResponseDto.setBody( question.getBody() );
        questionResponseDto.setViewCounting( question.getViewCounting() );
        if ( question.getQuestionStatus() != null ) {
            questionResponseDto.setQuestionStatus( question.getQuestionStatus().name() );
        }
        questionResponseDto.setCreateAt( question.getCreateAt() );
        questionResponseDto.setUpdateAt( question.getUpdateAt() );
        questionResponseDto.setQuestionVotes( questionVoteListToQuestionVoteResponseDtoList( question.getQuestionVotes() ) );
        questionResponseDto.setAnswers( answerListToAnswerResponseDtoList( question.getAnswers() ) );
        questionResponseDto.setQuestionComments( questionCommentListToQuestionCommentResponseDtoList( question.getQuestionComments() ) );

        return questionResponseDto;
    }

    protected List<QuestionResponseDto> questionListToQuestionResponseDtoList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionResponseDto> list1 = new ArrayList<QuestionResponseDto>( list.size() );
        for ( Question question : list ) {
            list1.add( questionToQuestionResponseDto( question ) );
        }

        return list1;
    }
}
