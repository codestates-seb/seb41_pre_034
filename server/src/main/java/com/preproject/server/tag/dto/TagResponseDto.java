package com.preproject.server.tag.dto;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TagResponseDto {

    private Long tagId;

    private String tag;

    private LocalDateTime createAt;

}
