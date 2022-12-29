package com.preproject.server.question.controller;

import com.preproject.server.constant.VoteStatus;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionVoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/question-vote")
@RequiredArgsConstructor
public class QuestionVoteController {

    private final QuestionMapper questionMapper;
    private final QuestionVoteService questionVoteService;


    //    질문의 추천 생성(UP/NONE/DOWN)
    @PostMapping("/{questionId}")
    public ResponseEntity postQuestionvote(
            @PathVariable Long questionId,
            @RequestBody QuestionVotePostDto questionVotePostDto
    ) {
        String voteStatus = questionVotePostDto.getVoteStatus().toUpperCase();
        questionVotePostDto.setVoteStatus(voteStatus);

        QuestionVote saved = questionVoteService.createVote(
                questionMapper.questionVotePostDtoToEntity(questionVotePostDto),
                questionId,
                questionVotePostDto.getUserId());

        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.questionVoteToQuestionVoteResponseDto(saved)),
                HttpStatus.CREATED);

    }

    //    추천 (UP/NONE/DOWN) 수정
    @PatchMapping("/vote/{questionVoteId}")
    public ResponseEntity patchQuestionvote(
            @PathVariable Long questionVoteId,
            @RequestBody QuestionVotePatchDto questionVotePatchDto) {
        String voteStatus = questionVotePatchDto.getVoteStatus().toUpperCase();
        questionVotePatchDto.setVoteStatus(voteStatus);

        QuestionVote questionVote =
                questionMapper.questionVotePatchDtoToEntity(questionVotePatchDto);

        QuestionVote patchQuestionVote =
                questionVoteService.updateVote(questionVote,questionVoteId);

        if (patchQuestionVote.getVoteStatus().equals(VoteStatus.NO_CONTENT)) {
            return ResponseEntity.noContent().build();
        }else {
            return new ResponseEntity<>(
                    ResponseDto.of(questionMapper.questionVoteToQuestionVoteResponseDto(patchQuestionVote)),
                    HttpStatus.OK);
        }

    }
}
