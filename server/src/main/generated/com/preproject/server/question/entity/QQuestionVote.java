package com.preproject.server.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionVote is a Querydsl query type for QuestionVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionVote extends EntityPathBase<QuestionVote> {

    private static final long serialVersionUID = -253924813L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionVote questionVote = new QQuestionVote("questionVote");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final QQuestion question;

    public final NumberPath<Long> questionVoteId = createNumber("questionVoteId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public final EnumPath<com.preproject.server.constant.VoteStatus> voteStatus = createEnum("voteStatus", com.preproject.server.constant.VoteStatus.class);

    public QQuestionVote(String variable) {
        this(QuestionVote.class, forVariable(variable), INITS);
    }

    public QQuestionVote(Path<? extends QuestionVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionVote(PathMetadata metadata, PathInits inits) {
        this(QuestionVote.class, metadata, inits);
    }

    public QQuestionVote(Class<? extends QuestionVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

