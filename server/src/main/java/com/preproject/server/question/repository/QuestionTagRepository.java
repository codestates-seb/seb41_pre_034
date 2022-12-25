package com.preproject.server.question.repository;

import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionTagRepository extends JpaRepository<QuestionTag, Long> {

    Page<QuestionTag> findAllByTag(Tag tag, Pageable pageable);
}
