package com.preproject.server.question.service;

import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.tag.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    public Question save(Question question) {

        Question saved = questionRepository.save(question);
        return saved;
    }



    public Question findVerifiedQuestion(long questionId) {
        Optional<Question> optionalQuestion =
                questionRepository.findById(questionId);
        Question findQuestion =
                optionalQuestion.orElseThrow(NullPointerException::new);

        return findQuestion;
    }

    public void deleteAll() {
        questionRepository.deleteAll();
    }

    public Question get(Long questionId) {

       Question question = findVerifiedQuestion(questionId);
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

    public Page<QuestionResponseDto> createQuestionResponseDtoPage(Pageable pageable, List<QuestionResponseDto> questionListToResponseDtoList) {

        List<QuestionResponseDto> questionResponseDto = questionListToResponseDtoList;
        return new PageImpl<>(
                questionResponseDto,
                pageable,
                questionResponseDto.size()
        );
    }

    public Question patch(Long questionId, Question question) {

        Question findQuestion = findVerifiedQuestion(questionId);

        Optional.ofNullable(findQuestion.getBody())
                .ifPresent(body -> findQuestion.setBody(body));

        Optional.ofNullable(findQuestion.getTitle())
                .ifPresent(title -> findQuestion.setTitle(title));


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
}
