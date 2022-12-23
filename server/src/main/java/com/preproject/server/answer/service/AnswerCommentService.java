package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.repository.AnswerCommentRepository;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerCommentService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final AnswerCommentRepository answerCommentRepository;

    public AnswerCommentService(AnswerRepository answerRepository,
                                UserRepository userRepository,
                                AnswerCommentRepository answerCommentRepository) {
        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.answerCommentRepository = answerCommentRepository;
    }

    public AnswerComment createAnswerComment(AnswerComment answerComment) {
        return answerCommentRepository.save(answerComment);
    }

    public AnswerComment findVerifiedAnswerComment (Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        // TODO: ExceptionCode
        Answer findAnswer = optionalAnswer.orElseThrow();

        return null;
    }

//    public AnswerComment updateAnswerComment(AnswerComment answerComment) {
//        AnswerComment findComment = findVerifiedAnswer()
//    }

    public void verfiyExistAnswer(Long answerId) {
        Optional<Answer> answer = answerRepository.findById(answerId);
        if (answer.isPresent()) { // TODO: ExceptionCode }
        }
    }
}
