package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.service.QuestionService;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AnswerService {
    private final AnswerRepository answerRepository;

    private final QuestionService questionService;

    private final UserService userService;

    public Answer createAnswer(
            Answer answer,
            Long questionId,
            Long userId
    ) {
        User findUser = userService.verifiedUserById(userId);
        Question findQuestion = questionService.get(questionId);
        answer.addQuestion(findQuestion);
        answer.addUser(findUser);
        answer.setCheck(false);
        return answerRepository.save(answer);
    }

    public Answer findAnswer(Long answerId) {
        Answer answer = verifiedAnswerById(answerId);

        return answer;
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }



    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = verifiedAnswerById(answer.getAnswerId());

        Optional.ofNullable(answer.getBody())
                .ifPresent(answerBody -> findAnswer.setBody(answerBody));

        Optional.ofNullable(answer.getCheck())
                .ifPresent(answerCheck->findAnswer.setCheck(answerCheck));

        if (findAnswer.getCheck()) {
            findAnswer.getQuestion().setQuestionStatus(QuestionStatus.CLOSED);
        }
        return findAnswer;
    }

    public Answer verifiedAnswerById(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        return findAnswer.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }
}
