package com.preproject.server.question.mapper;

import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.dto.QuestionCommentResponseDto;
import com.preproject.server.question.entity.QuestionComment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-23T23:00:22+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 17.0.3 (Eclipse Adoptium)"
)
@Component
public class QuestionCommentMapperImpl implements QuestionCommentMapper {

    @Override
    public QuestionComment QuestionCommentDtoToEntity(QuestionCommentPostDto questionCommentPostDto) {
        if ( questionCommentPostDto == null ) {
            return null;
        }

        QuestionComment questionComment = new QuestionComment();

        questionComment.setComment( questionCommentPostDto.getComment() );

        return questionComment;
    }

    @Override
    public QuestionCommentResponseDto QuestionCommentEntityToDto(QuestionComment questionComment) {
        if ( questionComment == null ) {
            return null;
        }

        QuestionCommentResponseDto questionCommentResponseDto = new QuestionCommentResponseDto();

        questionCommentResponseDto.setQuestionCommentId( questionComment.getQuestionCommentId() );
        questionCommentResponseDto.setComment( questionComment.getComment() );
        questionCommentResponseDto.setCreateAt( questionComment.getCreateAt() );
        questionCommentResponseDto.setUpdateAt( questionComment.getUpdateAt() );

        return questionCommentResponseDto;
    }
}
