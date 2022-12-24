package com.preproject.server.tag.service;

import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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


    public List<Tag> createTagByString(String tags) {
        String[] split = tags.split(",");
        List<Tag> tagList = Arrays.stream(split)
                .filter(tag -> !tag.isEmpty())
                .map(String::trim)
                .map(tag -> new Tag(tag, ""))
                .map(this::verifyTag)
                .collect(Collectors.toList());
        return tagList;
    }

    /* 검증 로직 */
    private Tag verifyTag(Tag tag) {
        Optional<Tag> findTag = tagRepository.findByTag(tag.getTag());
        return findTag.orElseGet(() -> tagRepository.save(tag));

    }
}
