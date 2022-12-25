package com.preproject.server.tag.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QTag is a Querydsl query type for Tag
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QTag extends EntityPathBase<Tag> {

    private static final long serialVersionUID = -1907975741L;

    public static final QTag tag1 = new QTag("tag1");

    public final DateTimePath<java.time.LocalDateTime> createAt = createDateTime("createAt", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final ListPath<com.preproject.server.question.entity.QuestionTag, com.preproject.server.question.entity.QQuestionTag> questionTags = this.<com.preproject.server.question.entity.QuestionTag, com.preproject.server.question.entity.QQuestionTag>createList("questionTags", com.preproject.server.question.entity.QuestionTag.class, com.preproject.server.question.entity.QQuestionTag.class, PathInits.DIRECT2);

    public final StringPath tag = createString("tag");

    public final NumberPath<Long> tagId = createNumber("tagId", Long.class);

    public final DateTimePath<java.time.LocalDateTime> updateAt = createDateTime("updateAt", java.time.LocalDateTime.class);

    public QTag(String variable) {
        super(Tag.class, forVariable(variable));
    }

    public QTag(Path<? extends Tag> path) {
        super(path.getType(), path.getMetadata());
    }

    public QTag(PathMetadata metadata) {
        super(Tag.class, metadata);
    }

}

