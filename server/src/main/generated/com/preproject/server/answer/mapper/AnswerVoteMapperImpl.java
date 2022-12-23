package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.constant.VoteStatus;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-24T00:04:54+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class AnswerVoteMapperImpl implements AnswerVoteMapper {

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

    @Override
    public AnswerVoteResponseDto EntityToResponseDto(AnswerVote answerVote) {
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
}
