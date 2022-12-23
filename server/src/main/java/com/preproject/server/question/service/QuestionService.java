package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {


    private final QuestionRepository questionRepository;

    private final UserRepository userRepository;

    public Question save(Question question) {

        question.setQuestionStatus(QuestionStatus.OPENED);
        question.setViewCounting(0);
        Question saved = questionRepository.save(question);

        return saved;
    }


    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }


    public Question get(Long questionId) {

        Question question = findVerifiedQuestion(questionId);
        question.setViewCounting(question.getViewCounting()+1);
        return question;
    }

    public void delete(Long questionId) {
        Question question = findVerifiedQuestion(questionId);
        questionRepository.delete(question);
    }


    public List<Question> findAll() {
        List<Question> questionList = questionRepository.findAll();
        return questionList;
    }

    public Question patch(Long questionId, Question question) {

        Question findQuestion = findVerifiedQuestion(questionId);

        Optional.ofNullable(question.getBody())
                .ifPresent(body -> findQuestion.setBody(body));

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));

//  to 태그 수정 미완성
//          Toto Tag patch
//        List<Tag> tagList = findQuestion.getQuestionTags().stream().map(questionTags -> {
//                    QuestionTag questionTag = new QuestionTag();
//                    Tag tag = new Tag();
//                    questionTag.setQuestion(questionTags.getQuestion());
//                    return questionTag.getTag();
//                }).collect(Collectors.toList());
//
//
//        System.out.println("tagList: "+tagList);

        return findQuestion;
    }

    private User verifiedUserById(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        return findUser.orElseThrow(
                () -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND)
        );
    }

}

