package com.preproject.server.user.controller;

import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.mapper.UserMapper;
import com.preproject.server.user.mapper.custom.CustomUserMapper;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Validated
public class UserController {

    private final UserService userService;

    private final UserMapper userMapper;

    private final CustomUserMapper customUserMapper;

    /* 사용자 단건 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable("userId") @NotNull Long userId
    ) {
        User user = userService.verifiedUserById(userId);
        UserResponseDto userResponseDto =
                customUserMapper.userEntityToResponseDto(user);
        return new ResponseEntity<>(
                ResponseDto.of(userResponseDto),
                HttpStatus.OK
                );
    }



    /* 사용자 페이지 전체 조회 */
    @GetMapping
    public ResponseEntity getUsers(
            @PageableDefault(page = 0, size = 10, sort = "userId", direction = Sort.Direction.DESC)
            Pageable pageable
    ) {
        Page<User> findUsers = userService.findUsers(pageable);
        List<UserSimpleResponseDto> userList =
                userMapper.UserListToResponseDtoList(findUsers.getContent());
        PageResponseDto response = PageResponseDto.of(
                userList,
                new PageImpl<>(
                        userList,
                        findUsers.getPageable(),
                        findUsers.getTotalElements()
                ));
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
        User save = userService.createUser(
                userMapper.UserPostDtoToEntity(userPostDto));
        return new ResponseEntity<>(
                ResponseDto.of(
                        userMapper.userEntityToSimpleResponseDto(save)
                ),
                HttpStatus.CREATED
        );
    }

    /* 사용자 정보 수정 */
    @PatchMapping("/{userId}")
    public ResponseEntity patchUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserPatchDto userPatchDto
    ) {

        User updateUser =
                userService.updateUser(userMapper.UserPatchDtoToEntity(userPatchDto));

        return new ResponseEntity<>(
                ResponseDto.of(
                        userMapper.userEntityToSimpleResponseDto(updateUser)
                ),
                HttpStatus.OK
        );
    }

    /* 사용자 전체 삭제 */
    @DeleteMapping
    public ResponseEntity deleteUsers() {
        userService.deleteUsers();
        return ResponseEntity.noContent().build();
    }

    /* 사용자 삭제 */
    @DeleteMapping("/{userId}")
    public ResponseEntity deleteUser(
            @PathVariable("userId") Long userId
    ) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }


}
