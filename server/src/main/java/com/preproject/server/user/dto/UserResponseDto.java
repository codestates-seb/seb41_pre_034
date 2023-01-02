package com.preproject.server.user.dto;

import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserResponseDto {

    private Long userId;

    private String email;

    private String displayName;

    private Boolean emailNotice;

    private String userStatus;

    private String loginType;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    // Todo 최근 작성 n 개 기준 출력
    private List<QuestionResponseDto> questions;

    private List<AnswerResponseDto> answers;

    private List<TagResponseDto> tags;


}
