package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerVoteService;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answer-vote")
@RequiredArgsConstructor
public class AnswerVoteController {

    private final AnswerMapper answerMapper;

    private final AnswerVoteService answerVoteService;

    // 1' 답변의 추천 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postVote(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerVotePostDto answerVotePostDto
    ) {
        String voteStatus = answerVotePostDto.getVoteStatus().toUpperCase();
        answerVotePostDto.setVoteStatus(voteStatus);

        AnswerVote save =
                answerVoteService.createVote(
                        answerMapper.answerVotePostDtoToEntity(answerVotePostDto),
                        answerId,
                        answerVotePostDto.getUserId());

        return new ResponseEntity<>(
                ResponseDto.of(answerMapper.answerVoteToAnswerVoteResponseDto(save)),
                HttpStatus.CREATED);
    }

    // 1' 추천 수정
    @PatchMapping("/vote/{answerVoteId}")
    public ResponseEntity patchVote(
            @PathVariable("answerVoteId") Long answerVoteId,
            @RequestBody AnswerVotePatchDto answerVotePatchDto
    ) {
        String voteStatus = answerVotePatchDto.getVoteStatus().toUpperCase();
        answerVotePatchDto.setVoteStatus(voteStatus);

        AnswerVote answerVote =
                answerMapper.answerVotePatchDtoToEntity(answerVotePatchDto);

        AnswerVote patchAnswerVote =
                answerVoteService.updateVote(answerVote, answerVoteId);

        if (patchAnswerVote.getVoteStatus().equals(VoteStatus.NO_CONTENT)) {
            return ResponseEntity.noContent().build();
        } else {
            return new ResponseEntity<>(
                    ResponseDto.of(answerMapper.answerVoteToAnswerVoteResponseDto(patchAnswerVote)),
                    HttpStatus.OK);
        }
    }
}
