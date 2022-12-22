package com.preproject.server.question.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionCommentPostDto {

    @NotBlank(message = "코멘트를 입력해야합니다.")
    private String comment;


    @Positive
    @NotBlank(message = "사용자의 ID를 입력해야합니다.")
    private Long userId;

}
