package com.preproject.server.answer.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswerComment is a Querydsl query type for AnswerComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswerComment extends EntityPathBase<AnswerComment> {

    private static final long serialVersionUID = -893811194L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswerComment answerComment = new QAnswerComment("answerComment");

    public final QAnswer answer;

    public final NumberPath<Long> answerCommentId = createNumber("answerCommentId", Long.class);

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public QAnswerComment(String variable) {
        this(AnswerComment.class, forVariable(variable), INITS);
    }

    public QAnswerComment(Path<? extends AnswerComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswerComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswerComment(PathMetadata metadata, PathInits inits) {
        this(AnswerComment.class, metadata, inits);
    }

    public QAnswerComment(Class<? extends AnswerComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.answer = inits.isInitialized("answer") ? new QAnswer(forProperty("answer"), inits.get("answer")) : null;
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

