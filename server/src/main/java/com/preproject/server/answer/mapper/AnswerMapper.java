package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.entity.Answer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerMapper {

    Answer answerPostDtoToEntity(AnswerPostDto answerPostDto);

    Answer answerPatchDtoToEntity(AnswerPatchDto answerPatchDto);

    AnswerResponseDto EntityToResponseDto(Answer answer);


}
