package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-23T22:18:10+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class AnswerMapperImpl implements AnswerMapper {

    @Override
    public Answer answerPostDtoToEntity(AnswerPostDto answerPostDto) {
        if ( answerPostDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setBody( answerPostDto.getBody() );

        return answer;
    }

    @Override
    public Answer answerPatchDtoToEntity(AnswerPatchDto answerPatchDto) {
        if ( answerPatchDto == null ) {
            return null;
        }

        Answer answer = new Answer();

        answer.setBody( answerPatchDto.getBody() );
        answer.setCheck( answerPatchDto.getCheck() );

        return answer;
    }

    @Override
    public AnswerResponseDto EntityToResponseDto(Answer answer) {
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
}
