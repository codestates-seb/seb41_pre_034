package com.preproject.server.question.repository;

import com.preproject.server.question.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    Page<Question> findAll(Pageable pageable);

    // Todo QueryDsl 동적 쿼리로 Tag 소유한 Question 탐색 해야 될듯.
//    Page<Question> findByQuestionTags(List<QuestionTag> questionTags, Pageable pageable);
}
