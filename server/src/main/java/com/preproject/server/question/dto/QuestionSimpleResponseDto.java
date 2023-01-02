package com.preproject.server.question.dto;


import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSimpleResponseDto {

    private Long questionId;

    private Long userId;

    private String displayName;

    private String title;

    private String body;

    private String questionStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private int countingVote;

    private int viewCounting;

    private int answerCounting;

    private List<String> tags;

}
