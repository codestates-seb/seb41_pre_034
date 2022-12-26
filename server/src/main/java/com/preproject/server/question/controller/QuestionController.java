package com.preproject.server.question.controller;

import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionPatchDto;
import com.preproject.server.question.dto.QuestionPostDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;
    private final QuestionMapper questionMapper;

    //    질문 생성
    @PostMapping
    public ResponseEntity postQuestion(
            @RequestBody QuestionPostDto questionPostDto) {

        Question question = questionMapper.questionPostDtoToEntity(questionPostDto);

        Question saved = questionService.save(
                question,
                questionPostDto.getTags(),
                questionPostDto.getUserId());

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

        Question patch =
                questionService.patch(
                        questionId,
                        questionMapper.questionPatchDtoToEntity(questionPatchDto),
                        questionPatchDto.getTags());


        return new ResponseEntity<>(
                ResponseDto.of(questionMapper.QuestionEntityToResponseDto(patch)),
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
    public ResponseEntity getQuestions(
            @PageableDefault(page = 0, size = 10, sort = "questionId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<Question> findQuestions = questionService.findAll(pageable);
        List<Question> questionList = findQuestions.getContent();
        List<QuestionSimpleResponseDto> questionResponseDtos =
                questionMapper.questionListToSimpleResponseDtoList(questionList);


        PageResponseDto response = PageResponseDto.of(
                questionResponseDtos
                , new PageImpl<>(
                        questionResponseDtos,
                        findQuestions.getPageable(),
                        findQuestions.getTotalElements()));

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

}
