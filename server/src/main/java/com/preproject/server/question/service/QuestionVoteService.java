package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.question.repository.QuestionVoteRepository;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionVoteService {


    private final QuestionVoteRepository questionVoteRepository;

    private final QuestionRepository questionRepository;

    private final UserService userService;

    private final QuestionService questionService;

    public QuestionVote createVote(
            QuestionVote questionVote,
            Long questionId,
            Long userId) {
        User user = userService.verifiedUserById(userId);
        Question findQuestion = questionRepository.findById(questionId)
                .orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));
        verifiedQuestion(findQuestion,userId);
        questionVote.addUser(user);
        questionVote.addQuestion(findQuestion);
        int countingVote = countingVote(findQuestion);
        findQuestion.setCountingVote(countingVote);
        return questionVoteRepository.save(questionVote);
    }

    public QuestionVote updateVote(QuestionVote questionVote, Long questionVoteId) {
        QuestionVote vote = findVerifiedQuestionVote(questionVoteId);
        VoteStatus comp = vote.getVoteStatus();
        if (questionVote.getVoteStatus() != null) {
            if (questionVote.getVoteStatus().equals(VoteStatus.UP)) {
                if (comp.equals(VoteStatus.UP)) {
                    QuestionVote noContentVote = new QuestionVote();
                    noContentVote.setVoteStatus(VoteStatus.NO_CONTENT);
                    return noContentVote;
                } else {
                    if (comp.equals(VoteStatus.NONE)) {
                        Optional.ofNullable(questionVote.getVoteStatus())
                                .ifPresent(vote::setVoteStatus);
                    } else {
                        vote.setVoteStatus(VoteStatus.NONE);
                    }
                }
            } else {
                if (comp.equals(VoteStatus.DOWN)) {
                    QuestionVote noContentVote = new QuestionVote();
                    noContentVote.setVoteStatus(VoteStatus.NO_CONTENT);
                    return noContentVote;
                } else {
                    if (comp.equals(VoteStatus.UP)) {
                        vote.setVoteStatus(VoteStatus.NONE);
                    }else {
                        Optional.ofNullable(questionVote.getVoteStatus())
                                .ifPresent(vote::setVoteStatus);
                    }
                }
            }
        }
        int countingVote = countingVote(vote.getQuestion());
        vote.getQuestion().setCountingVote(countingVote);
        return vote;
    }

    public QuestionVote findVerifiedQuestionVote(long questionVoteId) {
        Optional<QuestionVote> optionalQuestionVote =
                questionVoteRepository.findById(questionVoteId);
        QuestionVote questionVote =
                optionalQuestionVote.orElseThrow(
                        () -> new ServiceLogicException(ErrorCode.NOT_FOUND)
                );
        return questionVote;
    }

    private int countingVote(Question question) {
        if (!question.getQuestionVotes().isEmpty()) {
            int up = (int) question.getQuestionVotes()
                    .stream().filter(vote -> vote.getVoteStatus().equals(VoteStatus.UP)).count();
            int down = (int) question.getQuestionVotes()
                    .stream().filter(vote -> vote.getVoteStatus().equals(VoteStatus.DOWN)).count();
            return up - down;
        } else {
            return 0;
        }
    }

    private void verifiedQuestion(Question question, Long userId) {
        Optional<QuestionVote> first = question.getQuestionVotes().stream()
                .filter(vote -> vote.getUser().getUserId().equals(userId))
                .findFirst();
        if (first.isPresent()) {
            throw new ServiceLogicException(ErrorCode.VOTE_EXISTS);
        }
    }


}
