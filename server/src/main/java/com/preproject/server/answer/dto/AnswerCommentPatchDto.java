package com.preproject.server.answer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerCommentPatchDto {
    private Long userId;
    private String comment;

}
