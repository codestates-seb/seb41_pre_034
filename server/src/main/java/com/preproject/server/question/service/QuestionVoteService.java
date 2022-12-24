package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.repository.QuestionVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionVoteService {


    private final QuestionVoteRepository questionVoteRepository;

    public QuestionVote post(QuestionVote questionVote, Long questionId) {

        QuestionVote findQuestionVote = findVerifiedQuestionVote(questionId);


        if(questionVote.getQuestionVoteId() == findQuestionVote.getQuestionVoteId()){
            questionVoteRepository.save(questionVote);
        }

        return questionVote;
    }

    public QuestionVote patch(QuestionVote questionVote, Long questionVoteId) {

        QuestionVote vote = findVerifiedQuestionVote(questionVoteId);
        VoteStatus comp = vote.getVoteStatus();
        if (questionVote.getVoteStatus() != null) {
            if (questionVote.getVoteStatus().equals(VoteStatus.UP)) {
                if (comp.equals(VoteStatus.UP)) {
                    return vote;
                } else {
                    Optional.ofNullable(questionVote.getVoteStatus())
                            .ifPresent(vote::setVoteStatus);
                }
            } else {
                if (comp.equals(VoteStatus.DOWN)) {
                    return vote;
                } else {
                    Optional.ofNullable(questionVote.getVoteStatus())
                            .ifPresent(vote::setVoteStatus);
                }
            }
        }
        return vote;
    }



    public QuestionVote findVerifiedQuestionVote(long questionVoteId) {
        Optional<QuestionVote> optionalQuestionVote =
                questionVoteRepository.findById(questionVoteId);
        QuestionVote questionVote =
                optionalQuestionVote.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return questionVote;
    }
}
