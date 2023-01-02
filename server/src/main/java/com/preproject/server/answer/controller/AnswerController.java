package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerService;
import com.preproject.server.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
@Slf4j
public class AnswerController {

    private final AnswerMapper answerMapper;

    private final AnswerService answerService;


    /* 답변 생성 */
    @PostMapping
    public ResponseEntity postAnswer(
            @RequestBody AnswerPostDto answerPostDto
    ) {
        Answer save = answerService.createAnswer(
                answerMapper.answerPostDtoToEntity(answerPostDto),
                answerPostDto.getQuestionId(),
                answerPostDto.getUserId()
                );
        log.info("# Create Answer");
        return new ResponseEntity<>(
                ResponseDto.of(answerMapper.entityToResponseDto(save)),
                HttpStatus.CREATED);
    }

    /* 답변 수정 */
    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerPatchDto answerPatchDto
    ) {
        Answer answer =
                answerMapper.answerPatchDtoToEntity(answerPatchDto);
        answer.setAnswerId(answerId);
        Answer update = answerService.updateAnswer(answer);
        log.info("# Patch Answer");
        return new ResponseEntity<>(
                ResponseDto.of(answerMapper.entityToResponseDto(update)),
                HttpStatus.OK);
    }

    /* 답변 삭제 */
    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(
            @PathVariable("answerId") Long answerId
    ) {
        answerService.deleteAnswer(answerId);
        log.info("# Delete Answer");
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
