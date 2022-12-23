package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.AnswerVote;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerVoteMapper {
    AnswerVote answerVotePostDtoToEntity(AnswerVotePostDto answerVotePostDto);

    AnswerVote answerVotePatchDtoToEntity(AnswerVotePatchDto answerVotePatchDto);

    AnswerVoteResponseDto EntityToResponseDto(AnswerVote answerVote);
}
