package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.*;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
@RequiredArgsConstructor
public class AnswerVoteController {

    private final StubDtoUtils stubDtoUtils;


    // 1' 답변의 추천 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postVote(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerVotePostDto answerVotePostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createAnswerVoteResponseDto()),
                HttpStatus.CREATED);
    }

    // 1' 추천 수정
    @PatchMapping("/vote/{answerVoteId}")
    public ResponseEntity patchVote(
            @PathVariable("answerVoteId") Long answerVoteId,
            @RequestBody AnswerVotePatchDto answerVotePatchDto
    ) {
        AnswerVoteResponseDto response =
                stubDtoUtils.createAnswerVoteResponseDto();
        response.setVoteStatus("DOWN");
        return new ResponseEntity<>(
                ResponseDto.of(response),
                HttpStatus.OK);
    }
}
