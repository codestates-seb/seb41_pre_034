package com.preproject.server.question.service;

import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.repository.QuestionCommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class QuestionCommentService {

    @Autowired
    private QuestionCommentRepository questionCommentRepository;

    public QuestionComment save(QuestionComment questionComment, Long questionId) {
        if(questionComment.getQuestion().getQuestionId()==questionId){
            questionCommentRepository.save(questionComment);
        }
        return questionComment;
    }

    public void delete(Long questionCommentId) {
        QuestionComment questionComment = findVerifiedMember(questionCommentId);
        questionCommentRepository.delete(questionComment);

    }

    public QuestionComment findVerifiedMember(long questionId) {
        Optional<QuestionComment> optionalQuestionComment =
                questionCommentRepository.findById(questionId);
        QuestionComment findQuestionComment =
                optionalQuestionComment.orElseThrow(NullPointerException::new);

        return findQuestionComment;
    }
}
