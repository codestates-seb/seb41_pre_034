package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.repository.QuestionCommentRepository;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionCommentService {


    private final QuestionCommentRepository questionCommentRepository;

    private final UserRepository userRepository;

    private final QuestionRepository questionRepository;

    public QuestionComment save(QuestionComment questionComment) {

        QuestionComment save = questionCommentRepository.save(questionComment);

        return save;
    }

    public void delete(Long questionCommentId) {
        QuestionComment questionComment = findVerifiedMember(questionCommentId);
        questionCommentRepository.delete(questionComment);

    }

    public QuestionComment findVerifiedMember(long questionId) {
        Optional<QuestionComment> optionalQuestionComment =
                questionCommentRepository.findById(questionId);
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
