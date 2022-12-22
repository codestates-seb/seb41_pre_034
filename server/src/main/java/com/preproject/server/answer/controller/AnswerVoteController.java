package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.*;
import com.preproject.server.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
public class AnswerVoteController {

    // 1' 답변의 추천 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postVote(@PathVariable("answerId") Long answerId,
                                   @RequestBody AnswerVotePostDto answerVotePostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerVoteResponseDto()),
                HttpStatus.CREATED);
    }

    // 1' 추천 수정
    @PatchMapping("/vote/{answerVoteId}")
    public ResponseEntity patchVote(@PathVariable("answerVoteId") Long answerVoteId,
                                    @RequestBody AnswerVotePatchDto answerVotePatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerVoteResponseDto()),
                HttpStatus.OK);
    }
}
