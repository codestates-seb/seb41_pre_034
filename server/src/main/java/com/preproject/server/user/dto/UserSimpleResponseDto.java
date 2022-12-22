package com.preproject.server.user.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserSimpleResponseDto {

    private Long userId;

    private String email;

    private String displayName;

    private Boolean emailNotice;

    private String userStatus;

    private String loginType;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;
}
