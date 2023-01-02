package com.preproject.server.question.dto;


import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionCommentResponseDto {

    private Long questionCommentId;

    private Long userId;

    private String displayName;

    private String comment;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

}
