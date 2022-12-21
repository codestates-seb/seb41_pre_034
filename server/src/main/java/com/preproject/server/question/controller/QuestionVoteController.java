package com.preproject.server.question.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-vote")
public class QuestionVoteController {

//    질문의 추천 생성(UP/NONE/DOWN)
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionvote(@PathVariable Long questionId){
            // Todo QuestionVotePostDto
        return null;
    }

//    추천 (UP/NONE/DOWN) 수정
    @PatchMapping("/vote/{questionId}")
    public ResponseEntity patchQuestionvote(@PathVariable Long questionId
            // Todo QuestionVotePatchDto
    ){
        return null;
    }
}
