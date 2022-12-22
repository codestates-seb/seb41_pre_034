package com.preproject.server.answer.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerPatchDto {

    private Long userId;
    private Long questionId;
    private String body;
    private Boolean check;
}
