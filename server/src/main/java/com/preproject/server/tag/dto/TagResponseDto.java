package com.preproject.server.tag.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class TagResponseDto {

    private Long tagId;

    private String tag;

    private String description;

    private LocalDateTime createAt;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return tagId != null && tagId.equals(((TagResponseDto) obj).getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, description, createAt);
    }


}
