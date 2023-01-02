package com.preproject.server.tag.repository.querydsl;

import com.preproject.server.tag.dto.TagResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepositoryCustom {
    Page<TagResponseDto> findTagPageBySearchParams(String tag, Pageable pageable);
}
