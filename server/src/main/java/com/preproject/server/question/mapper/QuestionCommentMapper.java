package com.preproject.server.question.mapper;

import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.entity.QuestionComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface QuestionCommentMapper {
    QuestionComment QuestionCommentDtoToEntity(QuestionCommentPostDto questionCommentPostDto);

    QuestionCommentResponseDto QuestionCommentEntityToDto(QuestionComment questionComment);
}
