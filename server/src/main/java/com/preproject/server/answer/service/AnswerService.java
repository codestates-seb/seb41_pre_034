package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    public AnswerService(AnswerRepository answerRepository,
                         UserRepository userRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
    }

    public Answer createAnswer(Answer answer) {
        return answerRepository.save(answer);
    }

    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        // TODO: ExceptionCode
        Answer findAnswer = optionalAnswer.orElseThrow();

        return findAnswer;
    }

    public User findAnswerUser(Long answerId) {
        Answer findAnswer = findVerifiedAnswer(answerId);

        return findAnswer.getUser();
    }

    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = findVerifiedAnswer(answer.getAnswerId());

        Optional.ofNullable(answer.getBody())
                .ifPresent(answerBody -> findAnswer.setBody(answerBody));

        Optional.ofNullable(answer.getCheck())
                .ifPresent(answerCheck->findAnswer.setCheck(answerCheck));

        Answer updatedQuestion = answerRepository.save(findAnswer);

        return updatedQuestion;
    }
    public void verifyExistsUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) { // TODO: ExceptionCode }
        }
    }
}
