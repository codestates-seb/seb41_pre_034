package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final StubDtoUtils stubDtoUtils;


    /* 답변 생성 */
    @PostMapping
    public ResponseEntity postAnswer(
            @RequestBody AnswerPostDto answerPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createAnswerDto()),
                HttpStatus.CREATED);
    }

    /* 답변 수정 */
    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerPatchDto answerPatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createAnswerResponseDto()),
                HttpStatus.OK);
    }

    /* 답변 삭제 */
    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(
            @PathVariable("answerId") Long answerId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
