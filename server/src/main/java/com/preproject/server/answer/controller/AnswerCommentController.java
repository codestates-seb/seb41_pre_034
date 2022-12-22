package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-comment")
public class AnswerCommentController {

    // 1' 답변의 Comment 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postComment(@PathVariable("answerId") Long answerId,
                                      @RequestBody AnswerCommentPostDto answerCommentPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerCommentResponseDto()),
                HttpStatus.CREATED);
    }

    // 1' Comment 수정
    @PatchMapping("/comment/{answerCommentId}")
    public ResponseEntity patchComment(@PathVariable("answerCommentId") Long answerCommentId,
                                       @RequestBody AnswerCommentPatchDto answerCommentPatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerCommentResponseDto()),
                HttpStatus.OK);
    }

    // 1' Comment 삭제
    @DeleteMapping("/comment/{answerCommentId}")
    public ResponseEntity deleteComment(@PathVariable("answerCommentId") Long answerCommentId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
