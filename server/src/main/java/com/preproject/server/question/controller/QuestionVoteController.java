package com.preproject.server.question.controller;

import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionVoteService;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-vote")
@RequiredArgsConstructor
public class QuestionVoteController {

    private final StubDtoUtils stubDtoUtils;

    private final QuestionMapper questionMapper;
    private final QuestionVoteService questionVoteService;

    //    질문의 추천 생성(UP/NONE/DOWN)
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionvote(
            @PathVariable Long questionId,
            @RequestBody QuestionVotePostDto questionVotePostDto) {
        QuestionVote questionVote = questionMapper.questionVotePostDtoToEntity(questionVotePostDto);
        QuestionVote saved = questionVoteService.post(questionVote,questionId);

        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.questionVoteToQuestionVoteResponseDto(saved)),
                HttpStatus.CREATED);

    }

    //    추천 (UP/NONE/DOWN) 수정
    @PatchMapping("/vote/{questionVoteId}")
    public ResponseEntity patchQuestionvote(
            @PathVariable Long questionVoteId,
            @RequestBody QuestionVotePatchDto questionVotePatchDto) {

        QuestionVote questionVote = questionMapper.questionVotePatchDtoToEntity(questionVotePatchDto);

        QuestionVote newQuestionVote = questionVoteService.patch(questionVote,questionVoteId);

        QuestionVoteResponseDto response =
                questionMapper.questionVoteToQuestionVoteResponseDto(newQuestionVote);
        return new ResponseEntity<>(
                ResponseDto.of(response),
                HttpStatus.OK);
    }
}
