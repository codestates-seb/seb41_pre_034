package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.answer.repository.AnswerVoteRepository;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerVoteService {
    private final AnswerRepository answerRepository;
    private final AnswerVoteRepository answerVoteRepository;

    private final UserService userService;

    public AnswerVote createVote(
            AnswerVote answerVote,
            Long answerId,
            Long userId
    ) {
        User findUser = userService.verifiedUserById(userId);
        answerVote.addUser(findUser);
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
                    if (comp.equals(VoteStatus.UP)) {
                        vote.setVoteStatus(VoteStatus.NONE);
                    }else {
                        Optional.ofNullable(answerVote.getVoteStatus())
                                .ifPresent(vote::setVoteStatus);
                    }
                }
            }
        }
        int countingVote = countingVote(vote.getAnswer());
        vote.getAnswer().setCountingVote(countingVote);
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
    private int countingVote(Answer answer) {
        if (!answer.getAnswerVotes().isEmpty()) {
            int up = (int) answer.getAnswerVotes()
                    .stream().filter(vote -> vote.getVoteStatus().equals(VoteStatus.UP)).count();
            int down = (int) answer.getAnswerVotes()
                    .stream().filter(vote -> vote.getVoteStatus().equals(VoteStatus.DOWN)).count();
            return up - down;
        } else {
            return 0;
        }
    }
}
