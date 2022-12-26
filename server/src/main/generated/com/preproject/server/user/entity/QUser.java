package com.preproject.server.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -1580291725L;

    public static final QUser user = new QUser("user");

    public final ListPath<com.preproject.server.answer.entity.AnswerComment, com.preproject.server.answer.entity.QAnswerComment> answerComments = this.<com.preproject.server.answer.entity.AnswerComment, com.preproject.server.answer.entity.QAnswerComment>createList("answerComments", com.preproject.server.answer.entity.AnswerComment.class, com.preproject.server.answer.entity.QAnswerComment.class, PathInits.DIRECT2);

    public final ListPath<com.preproject.server.answer.entity.Answer, com.preproject.server.answer.entity.QAnswer> answers = this.<com.preproject.server.answer.entity.Answer, com.preproject.server.answer.entity.QAnswer>createList("answers", com.preproject.server.answer.entity.Answer.class, com.preproject.server.answer.entity.QAnswer.class, PathInits.DIRECT2);

    public final ListPath<com.preproject.server.answer.entity.AnswerVote, com.preproject.server.answer.entity.QAnswerVote> answerVotes = this.<com.preproject.server.answer.entity.AnswerVote, com.preproject.server.answer.entity.QAnswerVote>createList("answerVotes", com.preproject.server.answer.entity.AnswerVote.class, com.preproject.server.answer.entity.QAnswerVote.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath displayName = createString("displayName");

    public final StringPath email = createString("email");

    public final BooleanPath emailNotice = createBoolean("emailNotice");

    public final EnumPath<com.preproject.server.constant.LoginType> loginType = createEnum("loginType", com.preproject.server.constant.LoginType.class);

    public final StringPath password = createString("password");

    public final ListPath<com.preproject.server.question.entity.QuestionComment, com.preproject.server.question.entity.QQuestionComment> questionComments = this.<com.preproject.server.question.entity.QuestionComment, com.preproject.server.question.entity.QQuestionComment>createList("questionComments", com.preproject.server.question.entity.QuestionComment.class, com.preproject.server.question.entity.QQuestionComment.class, PathInits.DIRECT2);

    public final ListPath<com.preproject.server.question.entity.Question, com.preproject.server.question.entity.QQuestion> questions = this.<com.preproject.server.question.entity.Question, com.preproject.server.question.entity.QQuestion>createList("questions", com.preproject.server.question.entity.Question.class, com.preproject.server.question.entity.QQuestion.class, PathInits.DIRECT2);

    public final ListPath<com.preproject.server.question.entity.QuestionVote, com.preproject.server.question.entity.QQuestionVote> questionVotes = this.<com.preproject.server.question.entity.QuestionVote, com.preproject.server.question.entity.QQuestionVote>createList("questionVotes", com.preproject.server.question.entity.QuestionVote.class, com.preproject.server.question.entity.QQuestionVote.class, PathInits.DIRECT2);

    public final ListPath<String, StringPath> roles = this.<String, StringPath>createList("roles", String.class, StringPath.class, PathInits.DIRECT2);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public final EnumPath<com.preproject.server.constant.UserStatus> userStatus = createEnum("userStatus", com.preproject.server.constant.UserStatus.class);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

