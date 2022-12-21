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
    @PatchMapping("/comment/{commentId}")
    public ResponseEntity patchQuestionComment(@PathVariable Long commentId
               // Todo QuestionCommentPatchDto
    ){
        return null;
    }

//  질문의 Comment 삭제
    @DeleteMapping("/comment/{commentId}")
    public ResponseEntity deleteQuestionComment(@PathVariable Long commentId){
        return null;
    }
}
