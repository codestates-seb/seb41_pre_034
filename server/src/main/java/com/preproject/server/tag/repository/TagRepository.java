package com.preproject.server.tag.repository;

import com.preproject.server.tag.entity.QTag;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.repository.querydsl.TagRepositoryCustom;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long>,
        TagRepositoryCustom,
        QuerydslPredicateExecutor<Tag>,
        QuerydslBinderCustomizer<QTag>
{

    @Override
    default void customize(QuerydslBindings bindings, QTag root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.tag);
        bindings.bind(root.tag).as("tag").first(StringExpression::containsIgnoreCase);
    }

    Optional<Tag> findByTag(String tag);

    Page<Tag> findAll(Pageable pageable);

}
