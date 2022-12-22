package com.preproject.server.question.controller;

import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-vote")
public class QuestionVoteController {

//    질문의 추천 생성(UP/NONE/DOWN)
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionvote(@PathVariable Long questionId
                        , @RequestBody QuestionVotePostDto questionVotePostDto){
        return new ResponseEntity<>(
                ResponseDto.of(new QuestionVoteResponseDto()),
                HttpStatus.CREATED);

    }

//    추천 (UP/NONE/DOWN) 수정
    @PatchMapping("/vote/{questionVoteId}")
    public ResponseEntity patchQuestionvote(@PathVariable Long questionVoteId
            , @RequestBody QuestionVotePatchDto questionVotePatchDto){
        return new ResponseEntity<>(
                ResponseDto.of(new QuestionVoteResponseDto()),
                HttpStatus.CREATED);
    }
}
