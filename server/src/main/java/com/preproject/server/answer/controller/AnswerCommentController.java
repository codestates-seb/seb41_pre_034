package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerCommentPatchDto;
import com.preproject.server.answer.dto.AnswerCommentPostDto;
import com.preproject.server.answer.dto.AnswerCommentResponseDto;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerCommentService;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-comment")
@RequiredArgsConstructor
public class AnswerCommentController {

    private final AnswerMapper answerMapper;

    private final AnswerCommentService answerCommentService;

    private final UserService userService;


    // 1' 답변의 Comment 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postComment(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerCommentPostDto answerCommentPostDto
    ) {
        User findUser =
                userService.verifiedUserById(answerCommentPostDto.getUserId());

        AnswerComment answerComment =
                answerMapper.AnswerPostDtoToEntity(answerCommentPostDto);
        answerComment.addUser(findUser);

        AnswerComment save =
                answerCommentService.createComment(answerComment, answerId);

        AnswerCommentResponseDto response =
                answerMapper.answerCommentToAnswerCommentResponseDto(save);

        return new ResponseEntity<>(
                ResponseDto.of(response),
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

        AnswerCommentResponseDto response =
                answerMapper.answerCommentToAnswerCommentResponseDto(update);

        return new ResponseEntity<>(
                ResponseDto.of(response),
                HttpStatus.OK);
    }

    // 1' Comment 삭제
    @DeleteMapping("/comment/{answerCommentId}")
    public ResponseEntity deleteComment(
            @PathVariable("answerCommentId") Long answerCommentId
    ) {
        answerCommentService.delete(answerCommentId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
