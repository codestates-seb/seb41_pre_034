package com.preproject.server.question.repository.querydsl;

import com.preproject.server.answer.entity.QAnswer;
import com.preproject.server.answer.entity.QAnswerComment;
import com.preproject.server.answer.entity.QAnswerVote;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionSimpleDto;
import com.preproject.server.question.entity.*;
import com.preproject.server.tag.entity.QTag;
import com.preproject.server.user.entity.QUser;
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
    public Page<QuestionSimpleDto> findQuestionPageBySearchParams(
            String keyWord,
            String displayName,
            String tags,
            Pageable pageable
    ) {
        QQuestion question = QQuestion.question;

        JPQLQuery<QuestionSimpleDto> query = from(question)
                .select(
                        Projections.constructor(
                                QuestionSimpleDto.class,
                                question.questionId,
                                question.user.userId,
                                question.user.displayName,
                                question.title,
                                question.body,
                                question.questionStatus,
                                question.createAt,
                                question.updateAt,
                                question.countingVote,
                                question.viewCounting,
                                question.answerCounting,
                                question.tagString)
                );


        if (keyWord != null && !keyWord.isBlank()) {
            query
                    .where(question.title.containsIgnoreCase(keyWord))
                    .where(question.body.containsIgnoreCase(keyWord));
        }
        if (displayName != null && !displayName.isBlank()) {
            query.where(question.user.displayName.containsIgnoreCase(displayName));
        }
        if (tags != null && !tags.isBlank()) {
            query.where(question.tagString.containsIgnoreCase(tags));
        }
        List<QuestionSimpleDto> questionList = Optional.ofNullable(getQuerydsl())
                .orElseThrow(() -> new ServiceLogicException(
                        ErrorCode.INTERNAL_SERVER_ERROR))
                .applyPagination(pageable, query)
                .fetch();
        return new PageImpl<>(questionList, pageable, query.fetchCount());
    }

    @Override
    public Question findCustomById(Long questionId) {
        QQuestion question = QQuestion.question;
        QQuestionComment questionComment = QQuestionComment.questionComment;
        QQuestionVote questionVote = QQuestionVote.questionVote;
        QQuestionTag questionTag = QQuestionTag.questionTag;
        QUser user = QUser.user;
        QAnswer answer = QAnswer.answer;
        QAnswerComment answerComment = QAnswerComment.answerComment;
        QAnswerVote answerVote = QAnswerVote.answerVote;
        QTag tag = QTag.tag1;


        JPQLQuery<Question> query =
                from(question)
                        .leftJoin(question.questionComments,questionComment)
                        .fetchJoin()
                        .leftJoin(question.questionVotes,questionVote)
                        .fetchJoin()
                        .leftJoin(questionVote.user,user)
                        .fetchJoin()
                        .leftJoin(questionComment.user,user)
                        .fetchJoin()
                        .leftJoin(question.questionTags,questionTag)
                        .fetchJoin()
                        .leftJoin(questionTag.tag,tag)
                        .fetchJoin()
                        .leftJoin(question.user,user)
                        .fetchJoin()
                        .leftJoin(question.answers,answer)
                        .fetchJoin()
                        .leftJoin(answer.answerComments, answerComment)
                        .fetchJoin()
                        .leftJoin(answerComment.user,user)
                        .fetchJoin()
                        .leftJoin(answer.answerVotes, answerVote)
                        .fetchJoin()
                        .leftJoin(answerVote.user, user)
                        .fetchJoin()
                        .leftJoin(answer.user, user)
                        .fetchJoin()
                        .distinct()
                ;

        if (questionId != null) {
            query.where(question.questionId.eq(questionId));
        }
        return query.fetchOne();
    }
}
