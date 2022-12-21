package com.preproject.server.search.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/search")
public class SearchController {


    @GetMapping
    public ResponseEntity search(
            @RequestParam Map<String, Object> param
    ) {
        return null;
    }

    @GetMapping("/tag")
    public ResponseEntity searchTag(
            @RequestParam Map<String, Object> param
    ) {
        return null;
    }
}
