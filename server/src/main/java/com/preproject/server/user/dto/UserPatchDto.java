package com.preproject.server.user.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class UserPatchDto {

    private Long userId;

    private String password;

    private String displayName;

}
