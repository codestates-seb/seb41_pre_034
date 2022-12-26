package com.preproject.server.question.repository.querydsl;

import com.preproject.server.question.dto.QuestionSimpleDto;
import com.preproject.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface QuestionTagRepositoryCustom {

    Page<QuestionSimpleDto> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tag,
            Pageable pageable
    );

    Question findCustomById(Long questionId);
}
