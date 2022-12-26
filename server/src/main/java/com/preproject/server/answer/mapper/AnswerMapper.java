package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.*;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.List;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer answerPostDtoToEntity(AnswerPostDto answerPostDto);

    Answer answerPatchDtoToEntity(AnswerPatchDto answerPatchDto);

    AnswerComment AnswerPostDtoToEntity(AnswerCommentPostDto answerPostDto);

    AnswerComment AnswerPatchDtoToEntity(AnswerCommentPatchDto answerPatchDto);

    AnswerVote answerVotePostDtoToEntity(AnswerVotePostDto answerVotePostDto);

    AnswerVote answerVotePatchDtoToEntity(AnswerVotePatchDto answerVotePatchDto);


    default AnswerResponseDto entityToResponseDto(Answer answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setAnswerId( answer.getAnswerId() );
        answerResponseDto.setBody( answer.getBody() );
        answerResponseDto.setCheck( answer.getCheck() );
        answerResponseDto.setCreateAt( answer.getCreateAt() );
        answerResponseDto.setUpdateAt( answer.getUpdateAt() );
        answerResponseDto.setAnswerComments(
                answerCommentListToAnswerCommentResponseDtoList( answer.getAnswerComments() ) );
        answerResponseDto.setAnswerVotes(
                answerVoteListToAnswerVoteResponseDtoList( answer.getAnswerVotes() ) );

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

}
