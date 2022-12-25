package com.preproject.server.question.repository.querydsl;

import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionTagRepositoryCustom {

    Page<QuestionSimpleResponseDto> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tag,
            Pageable pageable
    );
}
