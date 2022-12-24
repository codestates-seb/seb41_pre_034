package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.answer.repository.AnswerVoteRepository;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerVoteService {
    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;

    public AnswerVote createVote(
            AnswerVote answerVote,
            Long answerId
    ) {
        Answer answer = verifiedAnswerById(answerId);
        answerVote.addAnswer(answer);
        return answerVoteRepository.save(answerVote);
    }

    public AnswerVote updateVote(AnswerVote answerVote, Long answerVoteId) {
        AnswerVote vote = verifiedAnswerVoteById(answerVoteId);
        VoteStatus comp = vote.getVoteStatus();
        if (answerVote.getVoteStatus() != null) {
            if (answerVote.getVoteStatus().equals(VoteStatus.UP)) {
                if (comp.equals(VoteStatus.UP)) {
                    return vote;
                } else {
                    Optional.ofNullable(answerVote.getVoteStatus())
                            .ifPresent(vote::setVoteStatus);
                }
            } else {
                if (comp.equals(VoteStatus.DOWN)) {
                    return vote;
                } else {
                    Optional.ofNullable(answerVote.getVoteStatus())
                            .ifPresent(vote::setVoteStatus);
                }
            }
        }

        return vote;
    }

    public Answer verifiedAnswerById(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        return findAnswer.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }

    public AnswerVote verifiedAnswerVoteById(Long answerVoteId) {
        Optional<AnswerVote> findVote = answerVoteRepository.findById(answerVoteId);
        return findVote.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }
}
