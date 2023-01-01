package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerCommentService;
import com.preproject.server.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-comment")
@RequiredArgsConstructor
@Slf4j
public class AnswerCommentController {

    private final AnswerMapper answerMapper;

    private final AnswerCommentService answerCommentService;



    // 1' 답변의 Comment 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postComment(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerCommentPostDto answerCommentPostDto
    ) {
        AnswerComment save =
                answerCommentService.createComment(
                        answerMapper.AnswerPostDtoToEntity(answerCommentPostDto),
                        answerId,
                        answerCommentPostDto.getUserId());
        log.info("# Create Answer Comment");
        return new ResponseEntity<>(
                ResponseDto.of(answerMapper.answerCommentToAnswerCommentResponseDto(save)),
                HttpStatus.CREATED);
    }

    // 1' Comment 수정
    @PatchMapping("/comment/{answerCommentId}")
    public ResponseEntity patchComment(
            @PathVariable("answerCommentId") Long answerCommentId,
            @RequestBody AnswerCommentPatchDto answerCommentPatchDto
    ) {
        AnswerComment answerComment =
                answerMapper.AnswerPatchDtoToEntity(answerCommentPatchDto);

        AnswerComment update =
                answerCommentService.updateComment(answerComment, answerCommentId);
        log.info("# Patch Answer Comment");
        return new ResponseEntity<>(
                ResponseDto.of(answerMapper.answerCommentToAnswerCommentResponseDto(update)),
                HttpStatus.OK);
    }

    // 1' Comment 삭제
    @DeleteMapping("/comment/{answerCommentId}")
    public ResponseEntity deleteComment(
            @PathVariable("answerCommentId") Long answerCommentId
    ) {
        answerCommentService.delete(answerCommentId);
        log.info("# Delete Answer Comment");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
