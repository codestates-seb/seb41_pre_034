package com.preproject.server.answer.mapper;

import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.entity.AnswerComment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-24T00:04:54+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class AnswerCommentMapperImpl implements AnswerCommentMapper {

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
    public AnswerCommentResponseDto EntityToAnswerResponseDto(AnswerComment answer) {
        if ( answer == null ) {
            return null;
        }

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto();

        answerCommentResponseDto.setAnswerCommentId( answer.getAnswerCommentId() );
        answerCommentResponseDto.setComment( answer.getComment() );
        answerCommentResponseDto.setCreateAt( answer.getCreateAt() );
        answerCommentResponseDto.setUpdateAt( answer.getUpdateAt() );

        return answerCommentResponseDto;
    }
}
