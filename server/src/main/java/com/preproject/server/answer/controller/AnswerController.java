package com.preproject.server.answer.controller;

import com.preproject.server.answer.dto.AnswerPatchDto;
import com.preproject.server.answer.dto.AnswerPostDto;
import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @PostMapping
    public ResponseEntity postAnswer(@RequestBody AnswerPostDto answerPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerResponseDto()),
                HttpStatus.CREATED);
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer(@PathVariable("answerId") Long answerId,
                                      @RequestBody AnswerPatchDto answerPatchDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(new AnswerResponseDto()),
                HttpStatus.OK);
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") Long answerId
    ) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
