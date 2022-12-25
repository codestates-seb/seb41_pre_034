package com.preproject.server.question.repository.querydsl;

import com.preproject.server.question.entity.QuestionTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionTagRepositoryCustom {

    Page<QuestionTag> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tag,
            Pageable pageable
    );
}
