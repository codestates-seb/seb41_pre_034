package com.preproject.server.answer.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/answers")
public class AnswerController {

    @PostMapping
    public ResponseEntity postAnswer( //TODO Dto

    ) {
        return null;
    }

    @PatchMapping("/{answerId}")
    public ResponseEntity patchAnswer(@PathVariable("answerId") Long answerId

    ) {
        return null;
    }

    @DeleteMapping("/{answerId}")
    public ResponseEntity deleteAnswer(@PathVariable("answerId") Long answerId

    ) {
        return null;
    }

}
