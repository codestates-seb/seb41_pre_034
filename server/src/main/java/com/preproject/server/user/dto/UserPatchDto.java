package com.preproject.server.user.dto;

import lombok.*;

import javax.validation.constraints.Pattern;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPatchDto {

    private Long userId;

    @Pattern(regexp = "^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "정확한 이메일을 입력해 주세요.")
    private String email;

    private String password;

    private String displayName;

}
