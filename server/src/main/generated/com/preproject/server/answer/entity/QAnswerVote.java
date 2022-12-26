package com.preproject.server.answer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswerVote is a Querydsl query type for AnswerVote
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswerVote extends EntityPathBase<AnswerVote> {

    private static final long serialVersionUID = 1203778723L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswerVote answerVote = new QAnswerVote("answerVote");

    public final QAnswer answer;

    public final NumberPath<Long> answerVoteId = createNumber("answerVoteId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public final EnumPath<com.preproject.server.constant.VoteStatus> voteStatus = createEnum("voteStatus", com.preproject.server.constant.VoteStatus.class);

    public QAnswerVote(String variable) {
        this(AnswerVote.class, forVariable(variable), INITS);
    }

    public QAnswerVote(Path<? extends AnswerVote> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswerVote(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswerVote(PathMetadata metadata, PathInits inits) {
        this(AnswerVote.class, metadata, inits);
    }

    public QAnswerVote(Class<? extends AnswerVote> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.answer = inits.isInitialized("answer") ? new QAnswer(forProperty("answer"), inits.get("answer")) : null;
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

