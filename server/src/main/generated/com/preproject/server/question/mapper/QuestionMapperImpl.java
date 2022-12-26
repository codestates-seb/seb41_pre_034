package com.preproject.server.question.mapper;

import com.preproject.server.constant.VoteStatus;
import com.preproject.server.question.dto.QuestionCommentPatchDto;
import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionVote;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-12-26T13:15:37+0900",
    comments = "version: 1.5.3.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.5.1.jar, environment: Java 11.0.16 (Azul Systems, Inc.)"
)
@Component
public class QuestionMapperImpl implements QuestionMapper {

    @Override
    public Question questionPostDtoToEntity(QuestionPostDto questionPostDto) {
        if ( questionPostDto == null ) {
            return null;
        }

        Question question = new Question();

        question.setTitle( questionPostDto.getTitle() );
        question.setBody( questionPostDto.getBody() );

        return question;
    }

    @Override
    public QuestionComment questionCommentDtoToEntity(QuestionCommentPostDto questionCommentPostDto) {
        if ( questionCommentPostDto == null ) {
            return null;
        }

        QuestionComment questionComment = new QuestionComment();

        questionComment.setComment( questionCommentPostDto.getComment() );

        return questionComment;
    }

    @Override
    public QuestionComment questionCommentPatchDtoToEntity(QuestionCommentPatchDto questionCommentPatchDto) {
        if ( questionCommentPatchDto == null ) {
            return null;
        }

        QuestionComment questionComment = new QuestionComment();

        questionComment.setComment( questionCommentPatchDto.getComment() );

        return questionComment;
    }

    @Override
    public QuestionVote questionVotePatchDtoToEntity(QuestionVotePatchDto questionVotePatchDto) {
        if ( questionVotePatchDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        if ( questionVotePatchDto.getVoteStatus() != null ) {
            questionVote.setVoteStatus( Enum.valueOf( VoteStatus.class, questionVotePatchDto.getVoteStatus() ) );
        }

        return questionVote;
    }

    @Override
    public QuestionVote questionVotePostDtoToEntity(QuestionVotePostDto questionVotePostDto) {
        if ( questionVotePostDto == null ) {
            return null;
        }

        QuestionVote questionVote = new QuestionVote();

        if ( questionVotePostDto.getVoteStatus() != null ) {
            questionVote.setVoteStatus( Enum.valueOf( VoteStatus.class, questionVotePostDto.getVoteStatus() ) );
        }

        return questionVote;
    }
}
