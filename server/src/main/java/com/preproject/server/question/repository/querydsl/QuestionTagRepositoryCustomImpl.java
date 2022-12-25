package com.preproject.server.question.repository.querydsl;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.QQuestionTag;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;

public class QuestionTagRepositoryCustomImpl
        extends QuerydslRepositorySupport implements QuestionTagRepositoryCustom{

    public QuestionTagRepositoryCustomImpl() {
        super(Question.class);
    }

    @Override
    public Page<QuestionTag> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tag,
            Pageable pageable
    ) {

        QQuestionTag qQuestionTag = QQuestionTag.questionTag;
        JPQLQuery<QuestionTag> query = from(qQuestionTag)
                .select(Projections.constructor(
                        QuestionTag.class,
                        qQuestionTag.questionTagId,
                        qQuestionTag.createAt,
                        qQuestionTag.updateAt,
                        qQuestionTag.question,
                        qQuestionTag.tag
                ));
        if (keyWord != null && !keyWord.isBlank()) {
            query.where(qQuestionTag.question.title.containsIgnoreCase(keyWord));
        }
        if (keyWord != null && !keyWord.isBlank()) {
            query.where(qQuestionTag.question.body.containsIgnoreCase(keyWord));
        }
        if (displayName != null && !displayName.isBlank()) {
            query.where(qQuestionTag.question.user.displayName.containsIgnoreCase(displayName));
        }
        if (tag != null && !tag.isBlank()) {
            query.where(qQuestionTag.tag.tag.containsIgnoreCase(tag));
        }
        List<QuestionTag> questionTags = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.INTERNAL_SERVER_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(questionTags, pageable, query.fetchCount());
    }
}
