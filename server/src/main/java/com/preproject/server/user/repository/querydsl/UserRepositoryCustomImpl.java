package com.preproject.server.user.repository.querydsl;

import com.preproject.server.answer.entity.QAnswer;
import com.preproject.server.answer.entity.QAnswerComment;
import com.preproject.server.answer.entity.QAnswerVote;
import com.preproject.server.question.entity.*;
import com.preproject.server.tag.entity.QTag;
import com.preproject.server.user.entity.QUser;
import com.preproject.server.user.entity.User;
import com.querydsl.jpa.JPQLQuery;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

public class UserRepositoryCustomImpl extends QuerydslRepositorySupport implements UserRepositoryCustom {

    public UserRepositoryCustomImpl() {
        super(User.class);
    }

    @Override
    public User findCustomById(Long userId) {
        QQuestion question = QQuestion.question;
        QQuestionComment questionComment = QQuestionComment.questionComment;
        QQuestionVote questionVote = QQuestionVote.questionVote;
        QQuestionTag questionTag = QQuestionTag.questionTag;
        QUser user = QUser.user;
        QAnswer answer = QAnswer.answer;
        QAnswerComment answerComment = QAnswerComment.answerComment;
        QAnswerVote answerVote = QAnswerVote.answerVote;
        QTag tag = QTag.tag1;

        JPQLQuery<User> query =
                from(user)
                        .leftJoin(user.questions,question)
                        .fetchJoin()
                        .leftJoin(question.questionComments,questionComment)
                        .fetchJoin()
                        .leftJoin(question.questionVotes,questionVote)
                        .fetchJoin()
                        .leftJoin(question.questionTags,questionTag)
                        .fetchJoin()
                        .leftJoin(questionTag.tag,tag)
                        .fetchJoin()
                        .leftJoin(question.answers,answer)
                        .fetchJoin()
                        .leftJoin(answer.answerComments, answerComment)
                        .fetchJoin()
                        .leftJoin(answer.answerVotes, answerVote)
                        .fetchJoin()
                        .leftJoin(user.answers,answer)
                        .fetchJoin()
                        .leftJoin(answer.answerComments, answerComment)
                        .fetchJoin()
                        .leftJoin(answer.answerVotes, answerVote)
                        .fetchJoin()
                        .distinct()
                ;

        if (userId != null) {
            query.where(user.userId.eq(userId));
        }
        return query.fetchOne();
    }
}
