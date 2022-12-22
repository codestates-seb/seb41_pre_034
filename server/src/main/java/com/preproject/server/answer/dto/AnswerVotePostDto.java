package com.preproject.server.answer.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerVotePostDto {
    @Positive
    @NotBlank(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "답변 투표 상태는 필수입니다")
    private String voteStatus;
}
