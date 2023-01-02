package com.preproject.server.answer.repository;

import com.preproject.server.answer.entity.AnswerVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerVoteRepository extends JpaRepository<AnswerVote, Long> {
}
