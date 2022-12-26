package com.preproject.server.tag.repository.querydsl;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.QTag;
import com.preproject.server.tag.entity.Tag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class TagRepositoryCustomImpl extends QuerydslRepositorySupport implements TagRepositoryCustom{

    public TagRepositoryCustomImpl() {
        super(Tag.class);
    }

    @Override
    public Page<TagResponseDto> findTagPageBySearchParams(
            String tag,
            Pageable pageable
    ) {

        QTag qTag = QTag.tag1;

        JPQLQuery<TagResponseDto> query = from(qTag)
                .select(Projections.constructor(
                        TagResponseDto.class,
                        qTag.tagId,
                        qTag.tag,
                        qTag.description,
                        qTag.createAt
                ));

        if (tag != null && !tag.isBlank()) {
            query.where(qTag.tag.containsIgnoreCase(tag));
        }

        List<TagResponseDto> tags = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.INTERNAL_SERVER_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(tags, pageable, query.fetchCount());
    }
}
