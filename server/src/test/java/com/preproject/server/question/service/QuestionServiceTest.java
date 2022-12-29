package com.preproject.server.question.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class QuestionServiceTest {

    @InjectMocks
    private QuestionService questionService;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserService userService;



    @Test
    @DisplayName("질문 수정 TEST")
    void patch() throws Exception {

        //given
        Question question=createQuestion(1L);
        given(questionRepository.findCustomById(anyLong()))
                .willReturn(null);
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionService.patch(2L,question, "java",1L));
        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.QUESTION_NOT_FOUND.getMessage());

    }

    @Test
    @DisplayName("질문 단건 조회 TEST")
    void get() throws Exception {
        //given

        given(questionRepository.findCustomById(anyLong())).willReturn(null);
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionService.get(2L));
        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.QUESTION_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("질문 단건 삭제 TEST")
    void delete() throws Exception{
        //given
        given(questionRepository.findCustomById(anyLong()))
                .willReturn(null);
        //when
        Throwable throwable = Assertions.catchThrowable(() -> questionService.delete(2L));
        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.QUESTION_NOT_FOUND.getMessage());
    }



    @Test
    @DisplayName("질문 서비스 buildTagString 메소드 TEST")
    void buildTagString() throws Exception{

        Set<QuestionTag> questionTaglist = new HashSet<QuestionTag>();
        QuestionTag questionTag = new QuestionTag();
        questionTag.setQuestion(createQuestion(1L));
        Tag tag =new Tag("java","descption");
        questionTag.setTag(tag);
        questionTaglist.add(questionTag);
        String tagString = questionService.buildTagString(questionTaglist);

        assertThat(tagString.equals("java"));



    }

        private Question createQuestion(Long questionId) {
            Question question = new Question("test-title", "test-body");

            return question;
    }

}