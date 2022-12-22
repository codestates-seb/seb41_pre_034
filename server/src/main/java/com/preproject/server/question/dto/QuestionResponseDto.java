package com.preproject.server.question.dto;

import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionResponseDto {

    private Long questionId;

    private Long userId;

    private String displayName;

    private String title;

    private String body;

    private String questionStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<QuestionVoteResponseDto> questionVotes;

    private int countingVote = questionVotes.size();

    private List<AnswerResponseDto> answers;

    private int countingAnswers = answers.size();

    private List<QuestionCommentResponseDto> questionComments;

    private List<TagResponseDto> tags;


}
