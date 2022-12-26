package com.preproject.server.question.repository;

import com.preproject.server.question.entity.QQuestion;
import com.preproject.server.question.entity.Question;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

public interface QuestionRepository extends JpaRepository<Question, Long>,
        QuerydslPredicateExecutor<Question>,
        QuerydslBinderCustomizer<QQuestion>
{

    @Override
    default void customize(QuerydslBindings bindings, QQuestion root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.title, root.body, root.user.displayName);
        bindings.bind(root.title).as("keyWord").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.body).as("keyWord").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.user.displayName).as("displayName").first(StringExpression::containsIgnoreCase);
    }

    Page<Question> findAll(Pageable pageable);

}
