package com.preproject.server.user.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    /* 사용자 단건 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable("userId") Long userId
    ) {
        return null;
    }

    /* 사용자 페이지 전체 조회 */
    @GetMapping
    public ResponseEntity getUsers(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        return null;
    }

    /* 사용자 생성 */
    @PostMapping
    public ResponseEntity createUser(
            // Todo UserPostDto
    ) {
        return null;
    }

    /* 사용자 정보 수정 */
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(
            @PathVariable("userId") Long userId
    ) {
        return null;
    }

    /* 사용자 전체 삭제 */
    @DeleteMapping
    public ResponseEntity deleteUsers() {
        return null;
    }

    /* 사용자 삭제 */
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") Long userId
    ) {
        return null;
    }
}