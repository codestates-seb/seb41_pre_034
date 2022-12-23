package com.preproject.server.question.mapper;

import com.preproject.server.question.dto.QuestionPatchDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {


    Question QuestionPostDtotoEntity(QuestionPostDto questionPostDto);

    QuestionResponseDto QuestionEntityToResponseDto(Question question);

    List<QuestionResponseDto> QuestionListToResponseDtoList(List<Question> questionList);

    Question QuestionPatchDtoToEntity(QuestionPatchDto questionPatchDto);
}
