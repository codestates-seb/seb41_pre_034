package com.preproject.server.question.dto;

import com.preproject.server.constant.QuestionStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionPatchDto {


    private String title;

    private String body;

    private Long userId;

    private String tags;
}
