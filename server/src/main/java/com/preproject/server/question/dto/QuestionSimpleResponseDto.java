package com.preproject.server.question.dto;


import com.preproject.server.constant.QuestionStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Setter
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

    private String tags;

    public QuestionSimpleResponseDto(
            Long questionId,
            Long userId,
            String displayName,
            String title,
            String body,
            QuestionStatus questionStatus,
            LocalDateTime createAt,
            LocalDateTime updateAt,
            int countingVote,
            int viewCounting,
            int answerCounting,
            String tags) {
        this.questionId = questionId;
        this.userId = userId;
        this.displayName = displayName;
        this.title = title;
        this.body = body;
        this.questionStatus = questionStatus.name();
        this.createAt = createAt;
        this.updateAt = updateAt;
        this.countingVote = countingVote;
        this.viewCounting = viewCounting;
        this.answerCounting = answerCounting;
        this.tags = tags;
    }

    public QuestionSimpleResponseDto() {
    }
}
