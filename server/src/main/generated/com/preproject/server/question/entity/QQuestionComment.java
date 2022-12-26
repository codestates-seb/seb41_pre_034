package com.preproject.server.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionComment is a Querydsl query type for QuestionComment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionComment extends EntityPathBase<QuestionComment> {

    private static final long serialVersionUID = -925522314L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionComment questionComment = new QQuestionComment("questionComment");

    public final StringPath comment = createString("comment");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final QQuestion question;

    public final NumberPath<Long> questionCommentId = createNumber("questionCommentId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public final com.preproject.server.user.entity.QUser user;

    public QQuestionComment(String variable) {
        this(QuestionComment.class, forVariable(variable), INITS);
    }

    public QQuestionComment(Path<? extends QuestionComment> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionComment(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionComment(PathMetadata metadata, PathInits inits) {
        this(QuestionComment.class, metadata, inits);
    }

    public QQuestionComment(Class<? extends QuestionComment> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.user = inits.isInitialized("user") ? new com.preproject.server.user.entity.QUser(forProperty("user")) : null;
    }

}

