package com.preproject.server.tag.controller;


import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
public class TagController {

    @GetMapping("/{tagId}")
    public ResponseEntity getQuestionsByTag(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return null;
    }

    @GetMapping
    public ResponseEntity getTags(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return null;
    }
}
