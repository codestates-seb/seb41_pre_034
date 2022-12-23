package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.answer.repository.AnswerVoteRepository;
import com.preproject.server.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnswerVoteService {
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;
    private final AnswerVoteRepository answerVoteRepository;

    public AnswerVoteService(AnswerRepository answerRepository,
                         UserRepository userRepository,
                             AnswerVoteRepository answerVoteRepository) {

        this.answerRepository = answerRepository;
        this.userRepository = userRepository;
        this.answerVoteRepository = answerVoteRepository;
    }

//    public AnswerVote answerVote(Long answerId, int vote) {
//
//    }


    public Answer findVerifiedAnswer(Long answerId) {
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        // TODO: ExceptionCode
        Answer findAnswer = optionalAnswer.orElseThrow();

        return findAnswer;
    }
}
