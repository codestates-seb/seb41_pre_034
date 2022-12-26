package com.preproject.server.question.dto;


import com.preproject.server.constant.QuestionStatus;
import lombok.*;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class QuestionSimpleDto {

    private Long questionId;

    private Long userId;

    private String displayName;

    private String title;

    private String body;

    private QuestionStatus questionStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private int countingVote;

    private int viewCounting;

    private int answerCounting;

    private String tags;
}
