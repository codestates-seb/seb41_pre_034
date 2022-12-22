package com.preproject.server.question.controller;

import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final StubDtoUtils stubDtoUtils;

    //    질문 생성
    @PostMapping
    public ResponseEntity postQuestion(
            @RequestBody QuestionPostDto questionPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createQuestionDto()),
                HttpStatus.CREATED);
    }

    //    질문 전체 삭제
    @DeleteMapping
    public ResponseEntity deleteQuestion() {
        return ResponseEntity.noContent().build();
    }

    //    질문 단건 조회
    @GetMapping("/{qestionId}")
    public ResponseEntity getQuestion(
            @PathVariable Long questionId
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createQuestionResponseDto()),
                HttpStatus.OK);
    }

    //    질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity patchQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionPostDto questionPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createQuestionResponseDto()),
                HttpStatus.OK);
    }

    //    질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(
            @PathVariable Long questionId
    ) {
        return ResponseEntity.noContent().build();
    }

    //    질문 전체 조회 페이지
    @GetMapping
    public ResponseEntity getQuestions(
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

}
