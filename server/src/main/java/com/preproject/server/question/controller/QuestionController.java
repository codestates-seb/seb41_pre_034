package com.preproject.server.question.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/questions")
public class QuestionController {

//    질문 생성
    @PostMapping
    public ResponseEntity postQuestion(
            // Todo QuestionPostDto
    ){
        return null;
    }

//    질문 전체 삭제
    @DeleteMapping
    public ResponseEntity deleteQuestion(){
        return null;
    }

//    질문 단건 조회
    @GetMapping("/{qestionId}")
    public ResponseEntity getQuestion(@PathVariable Long questionId){
        return null;
    }

//    질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity patchQuestion(@PathVariable Long questionId
        // Todo QuestionPatchDto
    ){
        return null;
    }

//    질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(@PathVariable Long questionId){
        return null;
    }

//    질문 전체 조회 페이지
    @GetMapping
    public ResponseEntity getQuestions(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ){
        return null;
    }

}
