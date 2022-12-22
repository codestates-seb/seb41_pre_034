package com.preproject.server.question.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionVoteResponseDto {

    private Long questionVoteId;

    private Long userId;

    private String voteStatus;

}
