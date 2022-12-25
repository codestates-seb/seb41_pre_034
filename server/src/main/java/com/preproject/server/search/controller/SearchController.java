package com.preproject.server.search.controller;


import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.service.TagService;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class SearchController {

    private final StubDtoUtils stubDtoUtils;

    private final TagService tagService;

    @GetMapping
    public ResponseEntity search(
            @RequestParam Map<String, Object> param,
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<QuestionResponseDto> questionResponseDtoPage =
                stubDtoUtils.createQuestionResponseDtoPage(pageable);
        PageResponseDto response = PageResponseDto.of(
                questionResponseDtoPage.getContent()
                , questionResponseDtoPage);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    @GetMapping("/tag")
    public ResponseEntity searchTag(
            @RequestParam Map<String, Object> param,
            @PageableDefault(page = 0, size = 36, sort = "tagId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<TagResponseDto> tags = tagService.findTags(pageable,param);
        List<TagResponseDto> dtos = tags.getContent();
        PageResponseDto response = PageResponseDto.of(
                dtos,
                new PageImpl<>(
                        dtos, tags.getPageable(), tags.getTotalElements()
                ));
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }
}
