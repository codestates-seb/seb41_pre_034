package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.repository.QuestionCommentRepository;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCommentService {

    private final QuestionCommentRepository questionCommentRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    private final QuestionService questionService;

    private final UserService userService;



    public QuestionComment save(
            QuestionComment questionComment,
            Long userId,
            Long questionId
    ) {
        User user = userService.verifiedUserById(userId);
        questionComment.addUser(user);
        Question verifiedQuestion = questionService.findVerifiedQuestion(questionId);
        questionComment.addQuestion(verifiedQuestion);

        return questionCommentRepository.save(questionComment);

    }

    public QuestionComment patch(
            QuestionComment questionComment,
            Long questionCommentId,
            Long userId
    ) {
        QuestionComment findQuestionComment = findVerifiedQuestionComment(questionCommentId);

        if (!Objects.equals(findQuestionComment.getUser().getUserId(), userId)) {
            throw new ServiceLogicException(ErrorCode.ACCESS_DENIED);
        }

        Optional.ofNullable(questionComment.getComment())
                .ifPresent(comment -> findQuestionComment.setComment(comment));

        return findQuestionComment;
    }

    public User findUser(Long userId) {
        User user = verifiedUserById(userId);
        return user;
    }


    public void delete(Long questionCommentId) {
        QuestionComment findQuestionComment = findVerifiedQuestionComment(questionCommentId);
        questionCommentRepository.delete(findQuestionComment);
    }





    public QuestionComment findVerifiedQuestionComment(long questionCommentId) {
        Optional<QuestionComment> optionalQuestionComment =
                questionCommentRepository.findById(questionCommentId);
        QuestionComment findQuestionComment =
                optionalQuestionComment.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return findQuestionComment;
    }

    private User verifiedUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        return findUser.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND)
        );

    }
}
