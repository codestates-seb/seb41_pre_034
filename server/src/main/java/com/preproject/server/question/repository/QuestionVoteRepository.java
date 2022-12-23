package com.preproject.server.question.repository;

import com.preproject.server.question.entity.QuestionVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionVoteRepository extends JpaRepository<QuestionVote, Long> {
}
