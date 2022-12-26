package com.preproject.server.question.repository;

import com.preproject.server.question.entity.QQuestionTag;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.repository.querydsl.QuestionTagRepositoryCustom;
import com.preproject.server.tag.entity.Tag;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long>,
        QuestionTagRepositoryCustom,
        QuerydslPredicateExecutor<QuestionTag>,
        QuerydslBinderCustomizer<QQuestionTag> {

    @Override
    default void customize(QuerydslBindings bindings, QQuestionTag root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.question.title,
                root.question.body,
                root.question.user.displayName,
                root.tag.tag
        );
        bindings.bind(root.question.title).as("keyWord").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.question.body).as("keyWord").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.question.user.displayName).as("displayName").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.tag.tag).as("tag").first(StringExpression::containsIgnoreCase);
    }

    Page<QuestionTag> findAllByTag(Tag tag, Pageable pageable);
}
