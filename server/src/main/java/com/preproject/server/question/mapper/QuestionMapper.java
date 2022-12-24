package com.preproject.server.question.mapper;

import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.question.dto.*;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.entity.QuestionVote;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {



    default Question questionPostDtoToEntity(QuestionPostDto questionPostDto) {
        if ( questionPostDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( questionPostDto.getTitle() );
        question.setBody( questionPostDto.getBody() );

        return question;
    }

    QuestionComment questionCommentDtoToEntity(QuestionCommentPostDto questionCommentPostDto);

    QuestionComment questionCommentPatchDtoToEntity(QuestionCommentPatchDto questionCommentPatchDto);

    QuestionVote questionVotePatchDtoToEntity(QuestionVotePatchDto questionVotePatchDto);

    QuestionVote questionVotePostDtoToEntity(QuestionVotePostDto questionVotePostDto);


    // Todo QuestionResponseDtoList 조회 구현 필요
    List<QuestionResponseDto> questionListToResponseDtoList(List<Question> questionList);

    default Question questionPatchDtoToEntity(QuestionPatchDto questionPatchDto) {
        if ( questionPatchDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( questionPatchDto.getTitle() );
        question.setBody( questionPatchDto.getBody() );

        return question;
    }

    default QuestionResponseDto QuestionEntityToResponseDto(Question question) {
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

        questionResponseDto.setUserId(question.getUser().getUserId());
        questionResponseDto.setDisplayName(question.getUser().getDisplayName());

        //Todo QuestionTag 매핑
//        questionResponseDto.setTags();
        List<QuestionTag> questionTags = question.getQuestionTags();

        return questionResponseDto;
    }

    default List<QuestionVoteResponseDto> questionVoteListToQuestionVoteResponseDtoList(List<QuestionVote> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionVoteResponseDto> list1 = new ArrayList<QuestionVoteResponseDto>( list.size() );
        for ( QuestionVote questionVote : list ) {
            list1.add( questionVoteToQuestionVoteResponseDto( questionVote ) );
        }

        return list1;
    }

    default QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote) {
        if ( questionVote == null ) {
            return null;
        }

        QuestionVoteResponseDto questionVoteResponseDto = new QuestionVoteResponseDto();

        questionVoteResponseDto.setQuestionVoteId( questionVote.getQuestionVoteId() );
        if ( questionVote.getVoteStatus() != null ) {
            questionVoteResponseDto.setVoteStatus( questionVote.getVoteStatus().name() );
        }
        questionVoteResponseDto.setUserId(questionVote.getUser().getUserId());

        return questionVoteResponseDto;
    }

    default List<AnswerResponseDto> answerListToAnswerResponseDtoList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerResponseDto> list1 = new ArrayList<AnswerResponseDto>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerToAnswerResponseDto( answer ) );
        }

        return list1;
    }

    default AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
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

        answerResponseDto.setUserId(answer.getUser().getUserId());
        answerResponseDto.setDisplayName(answer.getUser().getDisplayName());

        return answerResponseDto;
    }
    default List<AnswerCommentResponseDto> answerCommentListToAnswerCommentResponseDtoList(List<AnswerComment> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerCommentResponseDto> list1 = new ArrayList<AnswerCommentResponseDto>( list.size() );
        for ( AnswerComment answerComment : list ) {
            list1.add( answerCommentToAnswerCommentResponseDto( answerComment ) );
        }

        return list1;
    }
    default AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if ( answerComment == null ) {
            return null;
        }

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto();

        answerCommentResponseDto.setAnswerCommentId( answerComment.getAnswerCommentId() );
        answerCommentResponseDto.setComment( answerComment.getComment() );
        answerCommentResponseDto.setCreateAt( answerComment.getCreateAt() );
        answerCommentResponseDto.setUpdateAt( answerComment.getUpdateAt() );

        answerCommentResponseDto.setUserId(answerComment.getUser().getUserId());
        answerCommentResponseDto.setDisplayName(answerComment.getUser().getDisplayName());

        return answerCommentResponseDto;
    }
    default List<AnswerVoteResponseDto> answerVoteListToAnswerVoteResponseDtoList(List<AnswerVote> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerVoteResponseDto> list1 = new ArrayList<AnswerVoteResponseDto>( list.size() );
        for ( AnswerVote answerVote : list ) {
            list1.add( answerVoteToAnswerVoteResponseDto( answerVote ) );
        }

        return list1;
    }
    default AnswerVoteResponseDto answerVoteToAnswerVoteResponseDto(AnswerVote answerVote) {
        if ( answerVote == null ) {
            return null;
        }

        AnswerVoteResponseDto answerVoteResponseDto = new AnswerVoteResponseDto();

        answerVoteResponseDto.setAnswerVoteId( answerVote.getAnswerVoteId() );
        if ( answerVote.getVoteStatus() != null ) {
            answerVoteResponseDto.setVoteStatus( answerVote.getVoteStatus().name() );
        }
        answerVoteResponseDto.setUserId(answerVote.getUser().getUserId());

        return answerVoteResponseDto;
    }
    default List<QuestionCommentResponseDto> questionCommentListToQuestionCommentResponseDtoList(List<QuestionComment> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionCommentResponseDto> list1 = new ArrayList<QuestionCommentResponseDto>( list.size() );
        for ( QuestionComment questionComment : list ) {
            list1.add( questionCommentToQuestionCommentResponseDto( questionComment ) );
        }

        return list1;
    }

    default QuestionCommentResponseDto questionCommentToQuestionCommentResponseDto(QuestionComment questionComment) {
        if ( questionComment == null ) {
            return null;
        }

        QuestionCommentResponseDto questionCommentResponseDto = new QuestionCommentResponseDto();

        questionCommentResponseDto.setQuestionCommentId( questionComment.getQuestionCommentId() );
        questionCommentResponseDto.setComment( questionComment.getComment() );
        questionCommentResponseDto.setCreateAt( questionComment.getCreateAt() );
        questionCommentResponseDto.setUpdateAt( questionComment.getUpdateAt() );

        questionCommentResponseDto.setUserId(questionComment.getUser().getUserId());
        questionCommentResponseDto.setDisplayName(questionComment.getUser().getDisplayName());

        return questionCommentResponseDto;
    }
}
