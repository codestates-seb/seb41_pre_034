package com.preproject.server.answer.dto;

import lombok.*;

import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerCommentResponseDto {

    private Long answerCommentId;

    private Long userId;

    private String displayName;

    private String comment;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
