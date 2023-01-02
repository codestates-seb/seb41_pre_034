package com.preproject.server.question.controller;


import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionCommentPatchDto;
import com.preproject.server.question.dto.QuestionCommentPostDto;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionCommentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-comment")
@RequiredArgsConstructor
@Slf4j
public class QuestionCommentController {

    private final QuestionMapper questionMapper;

    private final QuestionCommentService questionCommentService;


    // 질문의 comment 생성
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionComment(
            @PathVariable Long questionId,
            @RequestBody QuestionCommentPostDto questionCommentPostDto
    ) {

        QuestionComment saved = questionCommentService.save(
                questionMapper.questionCommentDtoToEntity(questionCommentPostDto),
                questionCommentPostDto.getUserId(),
                questionId
                );
        log.info("# Create Question Comment");
        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.questionCommentToQuestionCommentResponseDto(saved)),
                HttpStatus.CREATED);
    }

    //  질문의  Comment 수정
    @PatchMapping("/comment/{questionCommentId}")
    public ResponseEntity patchQuestionComment(
            @PathVariable Long questionCommentId,
            @RequestBody QuestionCommentPatchDto questionCommentPatchDto
    ) {
        QuestionComment update = questionCommentService.patch(
                questionMapper.questionCommentPatchDtoToEntity(questionCommentPatchDto),
                questionCommentId,
                questionCommentPatchDto.getUserId());
        log.info("# Patch Question Comment");
        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.questionCommentToQuestionCommentResponseDto(update)),
                HttpStatus.OK);
    }

    //  질문의 Comment 삭제
    @DeleteMapping("/comment/{questionCommentId}")
    public ResponseEntity deleteQuestionComment(
            @PathVariable Long questionCommentId) {
        questionCommentService.delete(questionCommentId);
        log.info("# Delete Question Comment");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}


