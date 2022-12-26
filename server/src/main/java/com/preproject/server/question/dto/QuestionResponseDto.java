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

    private int viewCounting;

    private String questionStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private int countingVote;

    private int answerCounting;

    private List<QuestionVoteResponseDto> questionVotes;

    private List<AnswerResponseDto> answers;

    private List<QuestionCommentResponseDto> questionComments;

    private List<TagResponseDto> tags;


}
