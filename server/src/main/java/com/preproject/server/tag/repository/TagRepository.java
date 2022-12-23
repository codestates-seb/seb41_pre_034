package com.preproject.server.tag.repository;

import com.preproject.server.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByTag(String tag);

    Page<Tag> findAll(Pageable pageable);

}
