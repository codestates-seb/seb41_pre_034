package com.preproject.server.user.mapper.custom;

import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.mapper.AnswerMapper;
import com.preproject.server.question.dto.QuestionResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.mapper.TagMapper;
import com.preproject.server.user.dto.UserResponseDto;
import com.preproject.server.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomUserMapperImpl implements CustomUserMapper{

    private final QuestionMapper questionMapper;

    private final AnswerMapper answerMapper;

    private final TagMapper tagMapper;


    @Override
    public UserResponseDto userEntityToResponseDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setUserId( user.getUserId() );
        userResponseDto.setEmail( user.getEmail() );
        userResponseDto.setDisplayName( user.getDisplayName() );
        userResponseDto.setEmailNotice( user.getEmailNotice() );
        if ( user.getUserStatus() != null ) {
            userResponseDto.setUserStatus( user.getUserStatus().name() );
        }
        if ( user.getLoginType() != null ) {
            userResponseDto.setLoginType( user.getLoginType().name() );
        }
        userResponseDto.setCreateAt( user.getCreateAt() );
        userResponseDto.setUpdateAt( user.getUpdateAt() );
        userResponseDto.setQuestions( questionListToQuestionResponseDtoList( user.getQuestions() ) );
        userResponseDto.setAnswers( answerListToAnswerResponseDtoList( user.getAnswers() ) );

        return mapperUtil(userResponseDto);
    }

    protected List<QuestionResponseDto> questionListToQuestionResponseDtoList(List<Question> list) {
        if ( list == null ) {
            return null;
        }

        List<QuestionResponseDto> list1 = new ArrayList<QuestionResponseDto>( list.size() );
        for ( Question question : list ) {
            list1.add( questionMapper.QuestionEntityToResponseDto( question ) );
        }

        return list1;
    }

    protected List<AnswerResponseDto> answerListToAnswerResponseDtoList(List<Answer> list) {
        if ( list == null ) {
            return null;
        }

        List<AnswerResponseDto> list1 = new ArrayList<AnswerResponseDto>( list.size() );
        for ( Answer answer : list ) {
            list1.add( answerMapper.entityToResponseDto( answer ) );
        }

        return list1;
    }

    protected static UserResponseDto mapperUtil(
            UserResponseDto userResponseDto
    ) {
        List<QuestionResponseDto> questions =
                userResponseDto.getQuestions();
        List<AnswerResponseDto> answers =
                userResponseDto.getAnswers();
        List<TagResponseDto> tags = new ArrayList<>();
        questions.forEach(
                q -> tags.addAll(q.getTags())
        );
        userResponseDto.setTags(tags);

        if (!(questions.isEmpty()) && questions.size() > 4) {

            List<QuestionResponseDto> resultsQuestion = questions
                    .stream()
                    .skip(questions.size() - 5)
                    .collect(Collectors.toList());
            userResponseDto.setQuestions(resultsQuestion);
        }
        if (!(answers.isEmpty()) && answers.size() > 4) {
            List<AnswerResponseDto> resultAnswer = answers
                    .stream()
                    .skip(answers.size() - 5)
                    .collect(Collectors.toList());
            userResponseDto.setAnswers(resultAnswer);
        }
        return userResponseDto;
    }



}
