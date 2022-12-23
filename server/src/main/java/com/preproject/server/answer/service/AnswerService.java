package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    public Answer createAnswer(
            Answer answer
    ) {
        answer.setCheck(false);
        return answerRepository.save(answer);
    }

    public Answer findAnswer(Long answerId) {
        Answer answer = verifiedAnswerById(answerId);

        return answer;
    }

    public void deleteAnswer(Long answerId) {
        answerRepository.deleteById(answerId);
    }



    public Answer updateAnswer(Answer answer) {
        Answer findAnswer = verifiedAnswerById(answer.getAnswerId());

        Optional.ofNullable(answer.getBody())
                .ifPresent(answerBody -> findAnswer.setBody(answerBody));

        Optional.ofNullable(answer.getCheck())
                .ifPresent(answerCheck->findAnswer.setCheck(answerCheck));

        return findAnswer;
    }

    public Answer verifiedAnswerById(Long answerId) {
        Optional<Answer> findAnswer = answerRepository.findById(answerId);
        return findAnswer.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.ANSWER_NOT_FOUND)
        );
    }
}
