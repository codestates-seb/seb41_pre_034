package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionVoteResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.repository.QuestionVoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

        QuestionVote findQuestionVote = findVerifiedQuestionVote(questionVoteId);
        Optional.ofNullable(questionVote.getVoteStatus())
                .ifPresent(voteStatus -> findQuestionVote.setVoteStatus(voteStatus));

        return questionVote;
    }



    public QuestionVote findVerifiedQuestionVote(long questionVoteId) {
        Optional<QuestionVote> optionalQuestionVote =
                questionVoteRepository.findById(questionVoteId);
        QuestionVote questionVote =
                optionalQuestionVote.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return questionVote;
    }
}
