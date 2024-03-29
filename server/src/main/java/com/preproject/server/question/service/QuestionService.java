package com.preproject.server.question.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionSimpleDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.entity.QuestionVote;
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

import java.time.LocalDateTime;
import java.util.*;
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
        List<Tag> tagByString = tagService.createTagByString(tags);
        tagByString
                .forEach(tag -> new QuestionTag(question, tag));
        question.setQuestionStatus(QuestionStatus.OPENED);
        question.setViewCounting(0);
        question.setTagString(buildTagString(question.getQuestionTags()));
        question.setUpdateAt(LocalDateTime.now());
        question.addUser(user);
        return questionRepository.save(question);
    }


    public Question get(Long questionId) {
        Question question = findVerifiedQuestion(questionId);
        question.setViewCounting(question.getViewCounting() + 1);
        question.setAnswerCounting(countingAnswer(question.getAnswers()));
        question.setTagString(buildTagString(question.getQuestionTags()));
        return question;
    }



    public Question patch(Long questionId, Question question, String tags, Long userId) {
        Question findQuestion = findVerifiedQuestion(questionId);

        if (!Objects.equals(findQuestion.getUser().getUserId(), userId)) {
            throw new ServiceLogicException(ErrorCode.ACCESS_DENIED);
        }
        List<QuestionTag> questionTags = findQuestion.getQuestionTags()
                .stream().collect(Collectors.toList());
        findQuestion.setUpdateAt(LocalDateTime.now());

        Optional.ofNullable(question.getBody())
                .ifPresent(body -> findQuestion.setBody(body));

        Optional.ofNullable(question.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));

        if (!tags.isEmpty()) {
            List<Tag> tagByString = tagService.createTagByString(tags);
            Set<QuestionTag> addTags = tagByString.stream()
                    .map(tag -> new QuestionTag(findQuestion, tag))
                    .collect(Collectors.toSet());
            findQuestion.setQuestionTags(addTags);
        }
        questionTagRepository.deleteAll(questionTags);
        return findQuestion;
    }

    public Page<Question> findAll(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<QuestionSimpleDto> findAllByParam(
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
                Optional.ofNullable(questionRepository.findCustomById(questionId));
        Question findQuestion =
                optionalQuestion.orElseThrow(() -> new ServiceLogicException(ErrorCode.QUESTION_NOT_FOUND));

        return findQuestion;
    }

    /* util  메소드 */

    public String buildTagString(Set<QuestionTag> questionTags ) {
        if (questionTags == null) return "";
        List<Tag> tags = questionTags.stream()
                .map(QuestionTag::getTag)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();
        tags.forEach(t -> sb.append(t.getTag()).append(","));
        return sb.toString().replaceFirst(".$", "");
    }

    private int countingAnswer(Set<Answer> answers) {
        if (answers != null) {
            return answers.size();
        } else {
            return 0;
        }
    }


    // Todo 삭제
    private int countingVote(Set<QuestionVote> questionVotes) {
        if (questionVotes != null) {
            int up = (int) questionVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.UP.toString())).count();
            int down = (int) questionVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.DOWN.toString())).count();
            return up - down;
        } else {
            return 0;
        }
    }

}

