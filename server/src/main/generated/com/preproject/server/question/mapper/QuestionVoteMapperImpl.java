package com.preproject.server.question.mapper;

import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.QuestionVote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-23T20:37:58+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class QuestionVoteMapperImpl implements QuestionVoteMapper {

    @Override
    public QuestionVote QuestionVotePostDtoToEntity(QuestionVotePostDto questionVotePostDto) {
        if ( questionVotePostDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        if ( questionVotePostDto.getVoteStatus() != null ) {
            questionVote.setVoteStatus( Enum.valueOf( VoteStatus.class, questionVotePostDto.getVoteStatus() ) );
        }

        return questionVote;
    }

    @Override
    public QuestionVoteResponseDto QuestionVoteEntityToDto(QuestionVote saved) {
        if ( saved == null ) {
            return null;
        }

        QuestionVoteResponseDto questionVoteResponseDto = new QuestionVoteResponseDto();

        questionVoteResponseDto.setQuestionVoteId( saved.getQuestionVoteId() );
        if ( saved.getVoteStatus() != null ) {
            questionVoteResponseDto.setVoteStatus( saved.getVoteStatus().name() );
        }

        return questionVoteResponseDto;
    }

    @Override
    public QuestionVote QUestionVotePatchDtoToEntity(QuestionVotePatchDto questionVotePatchDto) {
        if ( questionVotePatchDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        if ( questionVotePatchDto.getVoteStatus() != null ) {
            questionVote.setVoteStatus( Enum.valueOf( VoteStatus.class, questionVotePatchDto.getVoteStatus() ) );
        }

        return questionVote;
    }
}
