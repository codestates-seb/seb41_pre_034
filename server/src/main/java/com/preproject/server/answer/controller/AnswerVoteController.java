package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerVotePatchDto;
import com.preproject.server.answer.dto.AnswerVotePostDto;
import com.preproject.server.answer.dto.AnswerVoteResponseDto;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.mapper.AnswerVoteMapper;
import com.preproject.server.answer.service.AnswerVoteService;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
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

    private final UserService userService;

    private final AnswerVoteMapper answerVoteMapper;

    private final AnswerVoteService answerVoteService;

    // 1' 답변의 추천 생성
    @PostMapping("/{answerId}")
    public ResponseEntity postVote(
            @PathVariable("answerId") Long answerId,
            @RequestBody AnswerVotePostDto answerVotePostDto
    ) {
        User findUser = userService.verifiedUserById(answerVotePostDto.getUserId());

        AnswerVote answerVote =
                answerVoteMapper.answerVotePostDtoToEntity(answerVotePostDto);
        answerVote.addUser(findUser);

        AnswerVote save =
                answerVoteService.createVote(answerVote, answerId);

        AnswerVoteResponseDto response =
                answerVoteMapper.EntityToResponseDto(save);
        response.setUserId(findUser.getUserId());

        return new ResponseEntity<>(
                ResponseDto.of(response),
                HttpStatus.CREATED);
    }

    // 1' 추천 수정
    @PatchMapping("/vote/{answerVoteId}")
    public ResponseEntity patchVote(
            @PathVariable("answerVoteId") Long answerVoteId,
            @RequestBody AnswerVotePatchDto answerVotePatchDto
    ) {
        AnswerVote answerVote =
                answerVoteMapper.answerVotePatchDtoToEntity(answerVotePatchDto);

        AnswerVote update =
                answerVoteService.updateVote(answerVote, answerVoteId);

        AnswerVoteResponseDto response =
                answerVoteMapper.EntityToResponseDto(update);
        response.setUserId(answerVotePatchDto.getUserId());

        return new ResponseEntity<>(
                ResponseDto.of(response),
                HttpStatus.OK);
    }
}
