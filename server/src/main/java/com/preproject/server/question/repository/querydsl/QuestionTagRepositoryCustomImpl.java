package com.preproject.server.question.repository.querydsl;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.QQuestion;
import com.preproject.server.question.entity.QQuestionTag;
import com.preproject.server.question.entity.Question;
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
    public Page<QuestionSimpleResponseDto> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tag,
            Pageable pageable
    ) {

        QQuestion qQuestion = QQuestion.question;
        QQuestionTag qQuestionTag = QQuestionTag.questionTag;
        JPQLQuery<QuestionSimpleResponseDto> query = from(qQuestion)
                .select(Projections.constructor(
                        QuestionSimpleResponseDto.class,
                        qQuestion.questionId,
                        qQuestion.user.userId,
                        qQuestion.user.displayName,
                        qQuestion.title,
                        qQuestion.body,
                        qQuestion.questionStatus,
                        qQuestion.createAt,
                        qQuestion.updateAt,
                        qQuestion.countingVote,
                        qQuestion.viewCounting,
                        qQuestion.answerCounting,
                        qQuestion.tagString
                ));
        if (keyWord != null && !keyWord.isBlank()) {
            query
                    .where(qQuestion.title.containsIgnoreCase(keyWord))
                    .where(qQuestion.body.containsIgnoreCase(keyWord));
        }
        if (displayName != null && !displayName.isBlank()) {
            query.where(qQuestion.user.displayName.containsIgnoreCase(displayName));
        }
        if (tag != null && !tag.isBlank()) {
            query.where(qQuestion.tagString.containsIgnoreCase(tag));
        }
        List<QuestionSimpleResponseDto> questionTags = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.INTERNAL_SERVER_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(questionTags, pageable, query.fetchCount());
    }
}
