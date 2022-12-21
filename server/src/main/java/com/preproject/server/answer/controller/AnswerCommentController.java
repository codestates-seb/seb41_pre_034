package com.preproject.server.answer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-comment")
public class AnswerCommentController {

    // 1' 답변의 Comment 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postComment(@PathVariable("answerId") Long answerId

    ) {
        return null;
    }

    // 1' Comment 수정
    @PatchMapping("/comment/{answerCommentId}")
    public ResponseEntity patchComment(@PathVariable("answerCommentId") Long answerCommentId
    ) {
        return null;
    }

    // 1' Comment 삭제
    @DeleteMapping("/comment/{answerCommentId}")
    public ResponseEntity deleteComment(@PathVariable("answerCommentId") Long answerCommentId
    ) {
        return null;
    }
}
