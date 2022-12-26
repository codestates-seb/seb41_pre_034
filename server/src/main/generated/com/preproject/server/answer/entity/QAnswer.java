package com.preproject.server.answer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswer is a Querydsl query type for Answer
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswer extends EntityPathBase<Answer> {

    private static final long serialVersionUID = -1290264039L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswer answer = new QAnswer("answer");

    public final SetPath<AnswerComment, QAnswerComment> answerComments = this.<AnswerComment, QAnswerComment>createSet("answerComments", AnswerComment.class, QAnswerComment.class, PathInits.DIRECT2);

    public final NumberPath<Long> answerId = createNumber("answerId", Long.class);

    public final SetPath<AnswerVote, QAnswerVote> answerVotes = this.<AnswerVote, QAnswerVote>createSet("answerVotes", AnswerVote.class, QAnswerVote.class, PathInits.DIRECT2);

    public final StringPath body = createString("body");

    public final BooleanPath check = createBoolean("check");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final com.preproject.server.question.entity.QQuestion question;

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public QAnswer(String variable) {
        this(Answer.class, forVariable(variable), INITS);
    }

    public QAnswer(Path<? extends Answer> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswer(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswer(PathMetadata metadata, PathInits inits) {
        this(Answer.class, metadata, inits);
    }

    public QAnswer(Class<? extends Answer> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new com.preproject.server.question.entity.QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

