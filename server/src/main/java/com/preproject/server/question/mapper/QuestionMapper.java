package com.preproject.server.question.mapper;

import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.question.dto.*;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
import org.mapstruct.Mapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface QuestionMapper {


    Question questionPostDtoToEntity(QuestionPostDto questionPostDto);

    QuestionComment questionCommentDtoToEntity(QuestionCommentPostDto questionCommentPostDto);

    QuestionComment questionCommentPatchDtoToEntity(QuestionCommentPatchDto questionCommentPatchDto);

    QuestionVote questionVotePatchDtoToEntity(QuestionVotePatchDto questionVotePatchDto);

    QuestionVote questionVotePostDtoToEntity(QuestionVotePostDto questionVotePostDto);


    default List<QuestionResponseDto> questionListToResponseDtoList(List<Question> questionList) {
        if (questionList == null) {
            return null;
        }

        List<QuestionResponseDto> list = new ArrayList<QuestionResponseDto>(questionList.size());
        for (Question question : questionList) {
            list.add(QuestionEntityToResponseDto(question));
        }

        return list;
    }

    default List<QuestionSimpleResponseDto> questionDtoListToSimpleResponseDtoList(List<QuestionSimpleDto> questionList) {
        if (questionList == null) {
            return null;
        }

        List<QuestionSimpleResponseDto> list = new ArrayList<QuestionSimpleResponseDto>(questionList.size());
        for (QuestionSimpleDto questionDto : questionList) {
            list.add(questionDtoToSimpleResponseDto(questionDto));
        }

        return list;
    }

    default List<QuestionSimpleResponseDto> questionListToSimpleResponseDtoList(List<Question> questionList) {
        if (questionList == null) {
            return null;
        }

        List<QuestionSimpleResponseDto> list = new ArrayList<QuestionSimpleResponseDto>(questionList.size());
        for (Question question : questionList) {
            list.add(questionEntityQuestionSimpleResponseDto(question));
        }

        return list;
    }

    default QuestionSimpleResponseDto questionDtoToSimpleResponseDto(QuestionSimpleDto questionDto) {
        if (questionDto == null) {
            return null;
        }
        QuestionSimpleResponseDto responseDto = new QuestionSimpleResponseDto();
        responseDto.setQuestionId(questionDto.getQuestionId());
        responseDto.setTitle(questionDto.getTitle());
        responseDto.setBody(questionDto.getBody());
        responseDto.setUserId(questionDto.getUserId());
        responseDto.setDisplayName(questionDto.getDisplayName());
        if (questionDto.getQuestionStatus() != null) {
            responseDto.setQuestionStatus(questionDto.getQuestionStatus().name());
        }
        responseDto.setCreateAt(questionDto.getCreateAt());
        responseDto.setUpdateAt(questionDto.getUpdateAt());
        responseDto.setCountingVote(questionDto.getCountingVote());
        responseDto.setViewCounting(questionDto.getViewCounting());
        responseDto.setAnswerCounting(questionDto.getAnswerCounting());
        List<String> tagList =
                Arrays.stream(questionDto.getTags().split(","))
                        .collect(Collectors.toList());
        responseDto.setTags(tagList);
        return responseDto;
    }


    default Question questionPatchDtoToEntity(QuestionPatchDto questionPatchDto) {
        if (questionPatchDto == null) {
            return null;
        }

        Question question = new Question();

        question.setTitle(questionPatchDto.getTitle());
        question.setBody(questionPatchDto.getBody());

        return question;
    }

    default QuestionSimpleResponseDto questionEntityQuestionSimpleResponseDto(Question question) {
        if (question == null) {
            return null;
        }
        QuestionSimpleResponseDto questionResponseDto = new QuestionSimpleResponseDto();

        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setBody(question.getBody());
        questionResponseDto.setUserId(question.getUser().getUserId());
        questionResponseDto.setDisplayName(question.getUser().getDisplayName());
        if (question.getQuestionStatus() != null) {
            questionResponseDto.setQuestionStatus(question.getQuestionStatus().name());
        }
        questionResponseDto.setCreateAt(question.getCreateAt());
        questionResponseDto.setUpdateAt(question.getUpdateAt());
        questionResponseDto.setCountingVote(question.getCountingVote());
        questionResponseDto.setViewCounting(question.getViewCounting());
        questionResponseDto.setAnswerCounting(question.getAnswerCounting());
        List<String> tagList =
                Arrays.stream(question.getTagString().split(","))
                        .collect(Collectors.toList());
        questionResponseDto.setTags(tagList);
        return questionResponseDto;
    }


    default QuestionResponseDto QuestionEntityToResponseDto(Question question) {
        if (question == null) {
            return null;
        }

        QuestionResponseDto questionResponseDto = new QuestionResponseDto();

        questionResponseDto.setQuestionId(question.getQuestionId());
        questionResponseDto.setTitle(question.getTitle());
        questionResponseDto.setBody(question.getBody());
        questionResponseDto.setViewCounting(question.getViewCounting());
        if (question.getQuestionStatus() != null) {
            questionResponseDto.setQuestionStatus(question.getQuestionStatus().name());
        }
        questionResponseDto.setCreateAt(question.getCreateAt());
        questionResponseDto.setUpdateAt(question.getUpdateAt());
        questionResponseDto.setQuestionVotes(questionVoteListToQuestionVoteResponseDtoList(new ArrayList<>(question.getQuestionVotes())));
        questionResponseDto.setAnswers(answerListToAnswerResponseDtoList(new ArrayList<>(question.getAnswers())));
        questionResponseDto.setQuestionComments(questionCommentListToQuestionCommentResponseDtoList(new ArrayList<>(question.getQuestionComments())));

        questionResponseDto.setUserId(question.getUser().getUserId());
        questionResponseDto.setDisplayName(question.getUser().getDisplayName());
        questionResponseDto.setCountingVote(question.getCountingVote());
        questionResponseDto.setAnswerCounting(question.getAnswerCounting());

        List<Tag> tags = question.getQuestionTags()
                .stream().map(QuestionTag::getTag).collect(Collectors.toList());
        questionResponseDto.setTags(tagListToTagResponseDtoList(tags));

        return questionResponseDto;
    }

    default List<TagResponseDto> tagListToTagResponseDtoList(List<Tag> tags) {
        if (tags == null) {
            return null;
        }

        List<TagResponseDto> list = new ArrayList<TagResponseDto>(tags.size());
        for (Tag tag : tags) {
            list.add(tagToTagResponseDto(tag));
        }

        return list;
    }

    default TagResponseDto tagToTagResponseDto(Tag tag) {
        if (tag == null) {
            return null;
        }

        TagResponseDto tagResponseDto = new TagResponseDto();

        tagResponseDto.setTagId(tag.getTagId());
        tagResponseDto.setTag(tag.getTag());
        tagResponseDto.setDescription(tag.getDescription());
        tagResponseDto.setCreateAt(tag.getCreateAt());

        return tagResponseDto;
    }

    default List<QuestionVoteResponseDto> questionVoteListToQuestionVoteResponseDtoList(List<QuestionVote> list) {
        if (list == null) {
            return null;
        }

        List<QuestionVoteResponseDto> list1 = new ArrayList<QuestionVoteResponseDto>(list.size());
        for (QuestionVote questionVote : list) {
            list1.add(questionVoteToQuestionVoteResponseDto(questionVote));
        }

        return list1;
    }

    default QuestionVoteResponseDto questionVoteToQuestionVoteResponseDto(QuestionVote questionVote) {
        if (questionVote == null) {
            return null;
        }

        QuestionVoteResponseDto questionVoteResponseDto = new QuestionVoteResponseDto();

        questionVoteResponseDto.setQuestionVoteId(questionVote.getQuestionVoteId());
        if (questionVote.getVoteStatus() != null) {
            questionVoteResponseDto.setVoteStatus(questionVote.getVoteStatus().name());
        }
        questionVoteResponseDto.setUserId(questionVote.getUser().getUserId());

        return questionVoteResponseDto;
    }

    default List<AnswerResponseDto> answerListToAnswerResponseDtoList(List<Answer> list) {
        if (list == null) {
            return null;
        }

        List<AnswerResponseDto> list1 = new ArrayList<AnswerResponseDto>(list.size());
        for (Answer answer : list) {
            list1.add(answerToAnswerResponseDto(answer));
        }

        return list1;
    }

    default AnswerResponseDto answerToAnswerResponseDto(Answer answer) {
        if (answer == null) {
            return null;
        }

        AnswerResponseDto answerResponseDto = new AnswerResponseDto();

        answerResponseDto.setAnswerId(answer.getAnswerId());
        answerResponseDto.setBody(answer.getBody());
        answerResponseDto.setCheck(answer.getCheck());
        answerResponseDto.setCreateAt(answer.getCreateAt());
        answerResponseDto.setUpdateAt(answer.getUpdateAt());
        answerResponseDto.setAnswerComments(answerCommentListToAnswerCommentResponseDtoList(new ArrayList<>(answer.getAnswerComments())));
        answerResponseDto.setAnswerVotes(answerVoteListToAnswerVoteResponseDtoList(new ArrayList<>(answer.getAnswerVotes())));

        answerResponseDto.setUserId(answer.getUser().getUserId());
        answerResponseDto.setDisplayName(answer.getUser().getDisplayName());
        answerResponseDto.setCountingVote(answer.getCountingVote());

        return answerResponseDto;
    }

    default List<AnswerCommentResponseDto> answerCommentListToAnswerCommentResponseDtoList(List<AnswerComment> list) {
        if (list == null) {
            return null;
        }

        List<AnswerCommentResponseDto> list1 = new ArrayList<AnswerCommentResponseDto>(list.size());
        for (AnswerComment answerComment : list) {
            list1.add(answerCommentToAnswerCommentResponseDto(answerComment));
        }

        return list1;
    }

    default AnswerCommentResponseDto answerCommentToAnswerCommentResponseDto(AnswerComment answerComment) {
        if (answerComment == null) {
            return null;
        }

        AnswerCommentResponseDto answerCommentResponseDto = new AnswerCommentResponseDto();

        answerCommentResponseDto.setAnswerCommentId(answerComment.getAnswerCommentId());
        answerCommentResponseDto.setComment(answerComment.getComment());
        answerCommentResponseDto.setCreateAt(answerComment.getCreateAt());
        answerCommentResponseDto.setUpdateAt(answerComment.getUpdateAt());

        answerCommentResponseDto.setUserId(answerComment.getUser().getUserId());
        answerCommentResponseDto.setDisplayName(answerComment.getUser().getDisplayName());

        return answerCommentResponseDto;
    }

    default List<AnswerVoteResponseDto> answerVoteListToAnswerVoteResponseDtoList(List<AnswerVote> list) {
        if (list == null) {
            return null;
        }

        List<AnswerVoteResponseDto> list1 = new ArrayList<AnswerVoteResponseDto>(list.size());
        for (AnswerVote answerVote : list) {
            list1.add(answerVoteToAnswerVoteResponseDto(answerVote));
        }

        return list1;
    }

    default AnswerVoteResponseDto answerVoteToAnswerVoteResponseDto(AnswerVote answerVote) {
        if (answerVote == null) {
            return null;
        }

        AnswerVoteResponseDto answerVoteResponseDto = new AnswerVoteResponseDto();

        answerVoteResponseDto.setAnswerVoteId(answerVote.getAnswerVoteId());
        if (answerVote.getVoteStatus() != null) {
            answerVoteResponseDto.setVoteStatus(answerVote.getVoteStatus().name());
        }
        answerVoteResponseDto.setUserId(answerVote.getUser().getUserId());

        return answerVoteResponseDto;
    }

    default List<QuestionCommentResponseDto> questionCommentListToQuestionCommentResponseDtoList(List<QuestionComment> list) {
        if (list == null) {
            return null;
        }

        List<QuestionCommentResponseDto> list1 = new ArrayList<QuestionCommentResponseDto>(list.size());
        for (QuestionComment questionComment : list) {
            list1.add(questionCommentToQuestionCommentResponseDto(questionComment));
        }

        return list1;
    }

    default QuestionCommentResponseDto questionCommentToQuestionCommentResponseDto(QuestionComment questionComment) {
        if (questionComment == null) {
            return null;
        }

        QuestionCommentResponseDto questionCommentResponseDto = new QuestionCommentResponseDto();

        questionCommentResponseDto.setQuestionCommentId(questionComment.getQuestionCommentId());
        questionCommentResponseDto.setComment(questionComment.getComment());
        questionCommentResponseDto.setCreateAt(questionComment.getCreateAt());
        questionCommentResponseDto.setUpdateAt(questionComment.getUpdateAt());

        questionCommentResponseDto.setUserId(questionComment.getUser().getUserId());
        questionCommentResponseDto.setDisplayName(questionComment.getUser().getDisplayName());

        return questionCommentResponseDto;
    }
}
