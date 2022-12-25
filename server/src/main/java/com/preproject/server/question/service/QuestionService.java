package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.question.repository.QuestionTagRepository;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.service.TagService;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionService {


    private final QuestionRepository questionRepository;

    private final QuestionTagRepository questionTagRepository;

    private final UserService userService;

    private final TagService tagService;

    public Question save(Question question, String tags, Long userId) {
        User user = userService.findUser(userId);
        question.addUser(user);

        List<Tag> tagByString = tagService.createTagByString(tags);
        question.setQuestionStatus(QuestionStatus.OPENED);
        question.setViewCounting(0);
        tagByString.forEach(tag -> new QuestionTag(question, tag));
        return questionRepository.save(question);
    }


    public Question get(Long questionId) {

        Question question = findVerifiedQuestion(questionId);
        question.setViewCounting(question.getViewCounting() + 1);
        return question;
    }



    public Question patch(Long questionId, Question question, String tags) {

        Question findQuestion = findVerifiedQuestion(questionId);

        Optional.ofNullable(question.getBody())
                .ifPresent(body -> findQuestion.setBody(body));

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));
        List<QuestionTag> questionTags = findQuestion.getQuestionTags();
        if (!tags.isEmpty()) {
            List<Tag> tagByString = tagService.createTagByString(tags);
            List<QuestionTag> addTags = tagByString.stream()
                    .map(tag -> new QuestionTag(findQuestion, tag))
                    .collect(Collectors.toList());
            findQuestion.setQuestionTags(addTags);
        }
        questionTagRepository.deleteAll(questionTags);
        return findQuestion;
    }

    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<QuestionTag> findAllByParam(
            Map<String, Object> param,
            Pageable pageable) {
        return questionTagRepository.findQuestionPageBySearchParams(
                (String) param.get("keyWord"),
                (String) param.get("displayName"),
                (String) param.get("tag"),
                pageable
        );
    }

    public void delete(Long questionId) {

        Question question = findVerifiedQuestion(questionId);
        questionRepository.delete(question);
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

}

