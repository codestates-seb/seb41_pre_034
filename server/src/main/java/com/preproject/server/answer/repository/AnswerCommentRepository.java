package com.preproject.server.answer.repository;

import com.preproject.server.answer.entity.AnswerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerCommentRepository extends JpaRepository<AnswerComment, Long> {
}
