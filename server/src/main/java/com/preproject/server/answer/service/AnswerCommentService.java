package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.repository.AnswerCommentRepository;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerCommentService {
    private final AnswerRepository answerRepository;
    private final AnswerCommentRepository answerCommentRepository;


    public AnswerComment createComment(
            AnswerComment answerComment,
            Long answerId
    ) {
        Answer answer = verifiedAnswerById(answerId);
        answerComment.addAnswer(answer);
        return answerCommentRepository.save(answerComment);
    }

    public AnswerComment updateComment(
            AnswerComment answerComment,
            Long answerCommentId
    ) {
        AnswerComment findEntity =
                verifiedAnswerCommentById(answerCommentId);

        Optional.ofNullable(answerComment.getComment())
                .ifPresent(ac -> findEntity.setComment(ac));
        return findEntity;
    }

    public void delete(Long answerCommentId) {
        AnswerComment comment = verifiedAnswerCommentById(answerCommentId);
        answerCommentRepository.delete(comment);
    }

    public Answer verifiedAnswerById(Long answerId) {
        Optional<Answer> findAnswer =
                answerRepository.findById(answerId);
        return findAnswer.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }

    public AnswerComment verifiedAnswerCommentById(Long answerCommentId) {
        Optional<AnswerComment> findComment =
                answerCommentRepository.findById(answerCommentId);
        return findComment.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }
}
