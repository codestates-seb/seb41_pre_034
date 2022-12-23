package com.preproject.server.question.controller;

import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionPatchDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final StubDtoUtils stubDtoUtils;
    private final QuestionService questionService;
    private final QuestionMapper questionMapper;
    private final UserService userService;

    //    질문 생성
    @PostMapping
    public ResponseEntity postQuestion(
            @RequestBody QuestionPostDto questionPostDto) {
        User user = userService.findUser(questionPostDto.getUserId());

        Question question = questionMapper.QuestionPostDtotoEntity(questionPostDto);
        question.addUser(user);
        Question saved = questionService.save(question);

        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.QuestionEntityToResponseDto(saved)),
                HttpStatus.CREATED);
    }

    //    질문 전체 삭제
    @DeleteMapping
    public ResponseEntity deleteQuestion() {
        questionService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    질문 단건 조회
    @Transactional(readOnly = true)
    @GetMapping("/{questionId}")
    public ResponseEntity getQuestion(
            @PathVariable Long questionId) {
        Question question = questionService.get(questionId);


        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.QuestionEntityToResponseDto(question)),
                HttpStatus.OK);
    }

    //    질문 수정
    @PatchMapping("/{questionId}")
    public ResponseEntity patchQuestion(
            @PathVariable Long questionId,
            @RequestBody QuestionPatchDto questionPatchDto) {

        Question question = questionMapper.QuestionPatchDtoToEntity(questionPatchDto);
        Question newQuestion = questionService.patch(questionId,question);


        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.QuestionEntityToResponseDto(newQuestion)),
                HttpStatus.OK);
    }

    //    질문 삭제
    @DeleteMapping("/{questionId}")
    public ResponseEntity deleteQuestion(
            @PathVariable Long questionId) {
        questionService.delete(questionId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    //    질문 전체 조회 페이지
    @GetMapping
    @Transactional(readOnly = true)
    public ResponseEntity getQuestions(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        List<Question> questionList = questionService.findAll();
        List<QuestionResponseDto> questionListToResponseDtoList = questionMapper.QuestionListToResponseDtoList(questionList);
        Page<QuestionResponseDto> questionResponseDtoPage =
                questionService.createQuestionResponseDtoPage(pageable,questionListToResponseDtoList);
        PageResponseDto response = PageResponseDto.of(
                questionResponseDtoPage.getContent()
                , questionResponseDtoPage);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

}
