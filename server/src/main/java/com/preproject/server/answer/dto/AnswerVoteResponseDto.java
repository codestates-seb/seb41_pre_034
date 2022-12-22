package com.preproject.server.answer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerVoteResponseDto {

    private Long answerVoteId;

    private Long userId;

    private String voteStatus;

}
