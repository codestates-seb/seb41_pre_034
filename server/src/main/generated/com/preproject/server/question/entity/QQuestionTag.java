package com.preproject.server.question.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionTag is a Querydsl query type for QuestionTag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionTag extends EntityPathBase<QuestionTag> {

    private static final long serialVersionUID = 268901169L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionTag questionTag = new QQuestionTag("questionTag");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final QQuestion question;

    public final NumberPath<Long> questionTagId = createNumber("questionTagId", Long.class);

    public final com.preproject.server.tag.entity.QTag tag;

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QQuestionTag(String variable) {
        this(QuestionTag.class, forVariable(variable), INITS);
    }

    public QQuestionTag(Path<? extends QuestionTag> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionTag(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionTag(PathMetadata metadata, PathInits inits) {
        this(QuestionTag.class, metadata, inits);
    }

    public QQuestionTag(Class<? extends QuestionTag> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestion(forProperty("question"), inits.get("question")) : null;
        this.tag = inits.isInitialized("tag") ? new com.preproject.server.tag.entity.QTag(forProperty("tag")) : null;
    }

}

