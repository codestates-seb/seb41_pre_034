package com.preproject.server.question.controller;


import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionCommentPatchDto;
import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-comment")
@RequiredArgsConstructor
public class QuestionCommentController {

    private final StubDtoUtils stubDtoUtils;


    // 질문의 comment 생성
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionComment(
            @PathVariable Long questionId,
            @RequestBody QuestionCommentPostDto questionCommentPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createQuestionCommentResponseDto()),
                HttpStatus.CREATED);
    }

    //  질문의  Comment 수정
    @PatchMapping("/comment/{questionCommentId}")
    public ResponseEntity patchQuestionComment(
            @PathVariable Long questionCommentId,
            @RequestBody QuestionCommentPatchDto questionCommentPatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createQuestionCommentResponseDto()),
                HttpStatus.OK);
    }

    //  질문의 Comment 삭제
    @DeleteMapping("/comment/{questionCommentId}")
    public ResponseEntity deleteQuestionComment(
            @PathVariable Long questionCommentId
    ) {
        return ResponseEntity.noContent().build();
    }
}
