package com.preproject.server.question.service;

import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.repository.QuestionVoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionVoteService {

    @Autowired
    private QuestionVoteRepository questionVoteRepository;

    public QuestionVote post(QuestionVote questionVote, Long questionId) {

        QuestionVote findQuestionVote = findVerifiedQuestionVote(questionId);


        if(questionVote.getQuestionVoteId() == findQuestionVote.getQuestionVoteId()){
            questionVoteRepository.save(questionVote);
        }

        return questionVote;
    }

    public QuestionVote patch(QuestionVote questionVote, Long questionVoteId) {

        QuestionVote findQuestionVote = findVerifiedQuestionVote(questionVoteId);
        Optional.ofNullable(questionVote.getVoteStatus())
                .ifPresent(voteStatus -> findQuestionVote.setVoteStatus(voteStatus));

        return questionVote;
    }



    public QuestionVote findVerifiedQuestionVote(long questionVoteId) {
        Optional<QuestionVote> optionalQuestionVote =
                questionVoteRepository.findById(questionVoteId);
        QuestionVote findQuestionVote =
                optionalQuestionVote.orElseThrow(NullPointerException::new);

        return findQuestionVote;
    }
}
