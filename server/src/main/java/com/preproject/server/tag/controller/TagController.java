package com.preproject.server.tag.controller;


import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final StubDtoUtils stubDtoUtils;

    @GetMapping("/{tagId}")
    public ResponseEntity getQuestionsByTag(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<QuestionResponseDto> questionResponseDtoPage =
                stubDtoUtils.createQuestionResponseDtoPage(pageable);
        PageResponseDto response = PageResponseDto.of(
                questionResponseDtoPage.getContent(),
                questionResponseDtoPage);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity getTags(
            @PageableDefault(page = 0, size = 10, sort = "tagId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<TagResponseDto> tagResponseDtoPage =
                stubDtoUtils.createTagResponseDtoPage(pageable);
        PageResponseDto response = PageResponseDto.of(
                tagResponseDtoPage.getContent(),
                tagResponseDtoPage);
        return new ResponseEntity<>(
                response,
                HttpStatus.OK);
    }
}
