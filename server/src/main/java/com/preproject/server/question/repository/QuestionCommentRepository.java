package com.preproject.server.question.repository;

import com.preproject.server.question.entity.QuestionComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionCommentRepository extends JpaRepository<QuestionComment, Long> {
}
