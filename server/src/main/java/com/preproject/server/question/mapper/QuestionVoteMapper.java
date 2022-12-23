package com.preproject.server.question.mapper;

import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.QuestionVote;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface QuestionVoteMapper {
    QuestionVote QuestionVotePostDtoToEntity(QuestionVotePostDto questionVotePostDto);

    QuestionVoteResponseDto QuestionVoteEntityToDto(QuestionVote saved);

    QuestionVote QUestionVotePatchDtoToEntity(QuestionVotePatchDto questionVotePatchDto);
}
