package com.preproject.server.answer.mapper;


import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.entity.AnswerComment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AnswerCommentMapper {

    AnswerComment AnswerPostDtoToEntity(AnswerCommentPostDto answerPostDto);

    AnswerComment AnswerPatchDtoToEntity(AnswerCommentPatchDto answerPatchDto);

    default AnswerCommentResponseDto EntityToAnswerResponseDto(AnswerComment answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto();

        answerCommentResponseDto.setAnswerCommentId( answer.getAnswerCommentId() );
        answerCommentResponseDto.setComment( answer.getComment() );
        answerCommentResponseDto.setCreateAt( answer.getCreateAt() );
        answerCommentResponseDto.setUpdateAt( answer.getUpdateAt() );
        answerCommentResponseDto.setUserId(answer.getUser().getUserId());
        answerCommentResponseDto.setDisplayName(answer.getUser().getDisplayName());

        return answerCommentResponseDto;
    }

}
