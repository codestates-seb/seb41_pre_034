package com.preproject.server.answer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
public class AnswerVoteController {

    // 1' 답변의 추천 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postVote(@PathVariable("answerId") Long answerId

    ) {
        return null;
    }

    // 1' 추천 수정
    @PatchMapping("/vote/{voteId}")
    public ResponseEntity patchVote(@PathVariable("voteId") Long voteId

    ) {
        return null;
    }
}
