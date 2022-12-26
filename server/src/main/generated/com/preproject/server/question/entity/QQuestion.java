package com.preproject.server.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestion is a Querydsl query type for Question
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestion extends EntityPathBase<Question> {

    private static final long serialVersionUID = -1110532183L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestion question = new QQuestion("question");

    public final NumberPath<Integer> answerCounting = createNumber("answerCounting", Integer.class);

    public final ListPath<com.preproject.server.answer.entity.Answer, com.preproject.server.answer.entity.QAnswer> answers = this.<com.preproject.server.answer.entity.Answer, com.preproject.server.answer.entity.QAnswer>createList("answers", com.preproject.server.answer.entity.Answer.class, com.preproject.server.answer.entity.QAnswer.class, PathInits.DIRECT2);

    public final StringPath body = createString("body");

    public final NumberPath<Integer> countingVote = createNumber("countingVote", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final ListPath<QuestionComment, QQuestionComment> questionComments = this.<QuestionComment, QQuestionComment>createList("questionComments", QuestionComment.class, QQuestionComment.class, PathInits.DIRECT2);

    public final NumberPath<Long> questionId = createNumber("questionId", Long.class);

    public final EnumPath<com.preproject.server.constant.QuestionStatus> questionStatus = createEnum("questionStatus", com.preproject.server.constant.QuestionStatus.class);

    public final ListPath<QuestionTag, QQuestionTag> questionTags = this.<QuestionTag, QQuestionTag>createList("questionTags", QuestionTag.class, QQuestionTag.class, PathInits.DIRECT2);

    public final ListPath<QuestionVote, QQuestionVote> questionVotes = this.<QuestionVote, QQuestionVote>createList("questionVotes", QuestionVote.class, QQuestionVote.class, PathInits.DIRECT2);

    public final StringPath tagString = createString("tagString");

    public final StringPath title = createString("title");

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public final NumberPath<Integer> viewCounting = createNumber("viewCounting", Integer.class);

    public QQuestion(String variable) {
        this(Question.class, forVariable(variable), INITS);
    }

    public QQuestion(Path<? extends Question> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestion(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestion(PathMetadata metadata, PathInits inits) {
        this(Question.class, metadata, inits);
    }

    public QQuestion(Class<? extends Question> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

