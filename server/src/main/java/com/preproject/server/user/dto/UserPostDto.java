package com.preproject.server.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPostDto {

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "정확한 이메일을 입력해 주세요.")
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String displayName;

    @NotBlank
    private Boolean emailNotice;

}
