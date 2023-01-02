package com.preproject.server.tag.controller;


import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.repository.QuestionTagRepository;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.mapper.TagMapper;
import com.preproject.server.tag.service.TagService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
@Slf4j
public class TagController {

    private final TagService tagService;

    private final QuestionTagRepository questionTagRepository;

    private final QuestionMapper questionMapper;

    private final TagMapper tagMapper;

    @GetMapping("/{tagId}")
    public ResponseEntity getQuestionsByTag(
            @PageableDefault(page = 0, size = 10, sort = "questionTagId", direction = Sort.Direction.DESC)
            Pageable pageable,
            @PathVariable Long tagId
    ) {

        Tag tag = tagService.findTag(tagId);

        Page<QuestionTag> tags = questionTagRepository.findAllByTag(tag, pageable);
        List<QuestionSimpleResponseDto> questionList = tags.getContent().stream()
                .map(QuestionTag::getQuestion)
                .map(questionMapper::questionEntityQuestionSimpleResponseDto)
                .collect(Collectors.toList());

        PageResponseDto response = PageResponseDto.of(
                questionList,
                new PageImpl<>(
                        questionList,
                        tags.getPageable(),
                        tags.getTotalElements()));
        log.info("# Questions By Tag Query");
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTags(
            @PageableDefault(page = 0, size = 36, sort = "tagId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Tag> tags = tagService.findTags(pageable);
        List<TagResponseDto> dtos =
                tagMapper.tagListToTagResponseDtoList(tags.getContent());
        PageResponseDto response = PageResponseDto.of(
                dtos,
                new PageImpl<>(
                        dtos, tags.getPageable(), tags.getTotalElements()
                ));
        log.info("# All Tag Query");
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }
}
