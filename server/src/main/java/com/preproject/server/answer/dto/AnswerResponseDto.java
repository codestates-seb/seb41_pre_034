package com.preproject.server.answer.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerResponseDto {

    private Long answerId;

    private Long userId;

    private String displayName;

    private String body;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<AnswerCommentResponseDto> answerComments;

    private List<AnswerVoteResponseDto> answerVotes;

    private int countingVote = answerVotes.size();

}
