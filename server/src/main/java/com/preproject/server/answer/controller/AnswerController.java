package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.answer.service.AnswerService;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.service.QuestionService;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
@RequiredArgsConstructor
public class AnswerController {

    private final StubDtoUtils stubDtoUtils;

    private final QuestionService questionService;

    private final UserService userService;

    private final AnswerMapper answerMapper;

    private final AnswerService answerService;


    /* 답변 생성 */
    @PostMapping
    public ResponseEntity postAnswer(
            @RequestBody AnswerPostDto answerPostDto
    ) {
        Long questionId = answerPostDto.getQuestionId();
        Long userId = answerPostDto.getUserId();

        User findUser = userService.verifiedUserById(userId);
        Question findQuestion = questionService.get(questionId);

        Answer answer =
                answerMapper.answerPostDtoToEntity(answerPostDto);

        answer.addQuestion(findQuestion);
        answer.addUser(findUser);

        Answer save = answerService.createAnswer(answer);

        AnswerResponseDto response =
                answerMapper.EntityToResponseDto(answer);

        response.setUserId(findUser.getUserId());
        response.setDisplayName(findUser.getDisplayName());

        return new ResponseEntity<>(
                ResponseDto.of(response),
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
        return new ResponseEntity<>(
                ResponseDto.of(update),
                HttpStatus.OK);
    }

    /* 답변 삭제 */
    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(
            @PathVariable("answerId") Long answerId
    ) {
        answerService.deleteAnswer(answerId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
