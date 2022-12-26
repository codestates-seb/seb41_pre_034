package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.constant.VoteStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T16:21:58+0900",
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
    public AnswerComment AnswerPostDtoToEntity(AnswerCommentPostDto answerPostDto) {
        if ( answerPostDto == null ) {
            return null;
        }

        AnswerComment answerComment = new AnswerComment();

        answerComment.setComment( answerPostDto.getComment() );

        return answerComment;
    }

    @Override
    public AnswerComment AnswerPatchDtoToEntity(AnswerCommentPatchDto answerPatchDto) {
        if ( answerPatchDto == null ) {
            return null;
        }

        AnswerComment answerComment = new AnswerComment();

        answerComment.setComment( answerPatchDto.getComment() );

        return answerComment;
    }

    @Override
    public AnswerVote answerVotePostDtoToEntity(AnswerVotePostDto answerVotePostDto) {
        if ( answerVotePostDto == null ) {
            return null;
        }

        AnswerVote answerVote = new AnswerVote();

        if ( answerVotePostDto.getVoteStatus() != null ) {
            answerVote.setVoteStatus( Enum.valueOf( VoteStatus.class, answerVotePostDto.getVoteStatus() ) );
        }

        return answerVote;
    }

    @Override
    public AnswerVote answerVotePatchDtoToEntity(AnswerVotePatchDto answerVotePatchDto) {
        if ( answerVotePatchDto == null ) {
            return null;
        }

        AnswerVote answerVote = new AnswerVote();

        if ( answerVotePatchDto.getVoteStatus() != null ) {
            answerVote.setVoteStatus( Enum.valueOf( VoteStatus.class, answerVotePatchDto.getVoteStatus() ) );
        }

        return answerVote;
    }
}
