package com.preproject.server.question.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-comment")
public class QuestionCommentController {

    // 질문의 comment 생성
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionComment(@PathVariable Long questionId){
             // Todo QuestionCommentPostDto
        return null;
    }

//  질문의  Comment 수정
    @PatchMapping("/comment/{questionCommentId}")
    public ResponseEntity patchQuestionComment(@PathVariable Long questionCommentId
               // Todo QuestionCommentPatchDto
    ){
        return null;
    }

//  질문의 Comment 삭제
    @DeleteMapping("/comment/{questionCommentId}")
    public ResponseEntity deleteQuestionComment(@PathVariable Long questionCommentId){
        return null;
    }
}
