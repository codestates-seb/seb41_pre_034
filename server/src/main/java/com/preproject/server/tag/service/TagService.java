package com.preproject.server.tag.service;

import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;


    public Tag createTag(Tag tag) {
        return verifyTag(tag);
    }

    public Page<Tag> findTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }


    /* 검증 로직 */
    private Tag verifyTag(Tag tag) {
        Optional<Tag> findTag = tagRepository.findByTag(tag.getTag());
        return findTag.orElseGet(() -> tagRepository.save(tag));

    }
}
