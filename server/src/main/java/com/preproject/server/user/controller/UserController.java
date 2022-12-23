package com.preproject.server.user.controller;

import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.dto.PageResponseDto;
import com.preproject.server.dto.ResponseDto;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.user.dto.UserPatchDto;
import com.preproject.server.user.dto.UserPostDto;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.dto.UserSimpleResponseDto;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.mapper.UserMapper;
import com.preproject.server.user.service.UserService;
import com.preproject.server.utils.StubDtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final StubDtoUtils stubDtoUtils;

    private final UserService userService;

    private final UserMapper userMapper;

    /* 사용자 단건 조회 */
    @GetMapping("/{userId}")
    public ResponseEntity getUser(
            @PathVariable("userId") Long userId
    ) {
        User user = userService.verifiedUserById(userId);
        UserResponseDto userResponseDto =
                userMapper.userEntityToResponseDto(user);
        return new ResponseEntity<>(
                ResponseDto.of(mapperUtil(userResponseDto)),
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

    private static UserResponseDto mapperUtil(
            UserResponseDto userResponseDto
    ) {
        List<QuestionResponseDto> questions =
                userResponseDto.getQuestions();
        List<AnswerResponseDto> answers =
                userResponseDto.getAnswers();
        if (!(questions.isEmpty()) && questions.size() > 4) {
            List<TagResponseDto> tags = new ArrayList<>();
            List<QuestionResponseDto> resultsQuestion = questions
                    .stream()
                    .skip(questions.size() - 5)
                    .collect(Collectors.toList());
            userResponseDto.setQuestions(resultsQuestion);
            resultsQuestion.forEach(
                    q -> tags.addAll(q.getTags())
            );
            userResponseDto.setTags(tags);
        }
        if (!(answers.isEmpty()) && answers.size() > 4) {
            List<AnswerResponseDto> resultAnswer = answers
                    .stream()
                    .skip(answers.size() - 5)
                    .collect(Collectors.toList());
            userResponseDto.setAnswers(resultAnswer);
        }
        return userResponseDto;
    }
}
