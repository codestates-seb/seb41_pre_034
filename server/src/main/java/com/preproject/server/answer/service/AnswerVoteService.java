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
        if (comp.equals(VoteStatus.NONE)) {

        } else if (comp.equals(VoteStatus.DOWN)) {

        } else {

        }

        Optional.ofNullable(answerVote.getVoteStatus())
                .ifPresent(voteStatus -> vote.setVoteStatus(voteStatus));

        return answerVote;
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
                // TODO: 수정 시 answerId가 아닌 answerVoteId를 가져오는게 맞는지
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }
}
