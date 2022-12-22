package com.preproject.server.user.controller;

import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final StubDtoUtils stubDtoUtils;

    /* 사용자 단건 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable("userId") Long userId
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createUserResponseDto()),
                HttpStatus.OK
                );
    }

    /* 사용자 페이지 전체 조회 */
    @GetMapping
    public ResponseEntity getUsers(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<UserResponseDto> userResponseDtoPage =
                stubDtoUtils.createUserResponseDtoPage(pageable);
        PageResponseDto response = PageResponseDto.of(
                userResponseDtoPage.getContent()
                , userResponseDtoPage);

        return new ResponseEntity<>(
                response,
                HttpStatus.OK
        );
    }

    /* 사용자 생성 */
    @PostMapping
    public ResponseEntity createUser(
            @RequestBody UserPostDto userPostDto
    ) {
        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createUserDto()),
                HttpStatus.CREATED
        );
    }

    /* 사용자 정보 수정 */
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserPatchDto userPatchDto
    ) {

        return new ResponseEntity<>(
                ResponseDto.of(stubDtoUtils.createUserResponseDto()),
                HttpStatus.OK
        );
    }

    /* 사용자 전체 삭제 */
    @DeleteMapping
    public ResponseEntity deleteUsers() {
        return ResponseEntity.ok().build();
    }

    /* 사용자 삭제 */
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") Long userId
    ) {
        return ResponseEntity.ok().build();
    }
}
