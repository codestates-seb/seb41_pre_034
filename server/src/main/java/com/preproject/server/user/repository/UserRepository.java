package com.preproject.server.user.repository;

import com.preproject.server.user.entity.QUser;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.querydsl.UserRepositoryCustom;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>,
        UserRepositoryCustom,
        QuerydslPredicateExecutor<User>,
        QuerydslBinderCustomizer<QUser>
{

    @Override
    default void customize(QuerydslBindings bindings, QUser root) {
        bindings.excludeUnlistedProperties(true);
        bindings.including(root.email, root.displayName);
        bindings.bind(root.email).as("email").first(StringExpression::containsIgnoreCase);
        bindings.bind(root.displayName).as("displayName").first(StringExpression::containsIgnoreCase);
    }

    Optional<User> findByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Optional<User> findByDisplayName(String displayName);

}
