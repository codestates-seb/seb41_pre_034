package com.preproject.server.answer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerVotePatchDto {

    private Long userId;
    private String voteStatus;
}
