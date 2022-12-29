package com.preproject.server.question.service;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.repository.QuestionCommentRepository;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.user.entity.User;
import com.preproject.server.user.repository.UserRepository;
import com.preproject.server.user.service.UserService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class QuestionCommentServiceTest {


    @InjectMocks
    private QuestionCommentService questionCommentService;

    @Mock
    private QuestionCommentService QuestionCommentService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private QuestionCommentRepository questionCommentRepository;

    @Mock
    private QuestionRepository questionRepository;

    @Mock
    private UserService userService;


    @Test
    @DisplayName("질문 코멘트 수정 TEST_1")
    void patch1() throws Exception{
        // given
        given(questionCommentRepository.findById(anyLong()))
                .willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionCommentService.patch(createQuestionComment(1L)
                        ,1L,1L));

        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.QUESTION_NOT_FOUND.getMessage());
    }



    @Test
    @DisplayName("질문 코멘트 수정 TEST_2")
    void patch2() throws Exception{

        // given
        QuestionComment questionComment1 = createQuestionComment(1L);

        User user1 = createUser(1L);
        User user2 = createUser(2L);
        questionComment1.setUser(user1);
        QuestionComment questionComment2 = new QuestionComment("comment1");
        questionComment2.setUser(user2);

        given(questionCommentRepository.findById(anyLong())).willReturn(Optional.of(questionComment2));

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionCommentService.patch(questionComment2,1L,1L));
        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ACCESS_DENIED.getMessage());
    }


    @Test
    @DisplayName("질문 코멘트 수정 TEST_3")
    void patch3() throws Exception{

        // given
        QuestionComment questionComment1 = createQuestionComment(1L);
        User user1 = createUser(1L);
        questionComment1.addUser(user1);
        QuestionComment questionComment2 = new QuestionComment("comment1");
        questionComment2.addUser(user1);

        // when
        given(questionCommentRepository.findById(anyLong())).willReturn(Optional.of(questionComment2));
        QuestionComment patch = questionCommentService.patch(questionComment1, 1L, 1L);

        // then
        Assertions.assertThat(patch.getComment().equals(questionComment1.getComment()));
    }

    @Test
    void delete() throws Exception{
        // given
        given(questionCommentRepository.findById(anyLong()))
                .willReturn(Optional.empty());
        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionCommentService.delete(1L));
        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.QUESTION_NOT_FOUND.getMessage());

    }



    private QuestionComment createQuestionComment(Long questionCommentId){
        QuestionComment questionComment = new QuestionComment();
        questionComment.setComment("comment");
        return questionComment;
    }

    private User createUser(Long userId){
        User user = new User();
        user.setUserId(userId);
        user.setEmail("email");
        user.setPassword("pass");
        user.setDisplayName("name");
        user.setEmailNotice(true);
        return user;
    }

}