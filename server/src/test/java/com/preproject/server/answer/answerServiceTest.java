package com.preproject.server.answer;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.answer.service.AnswerService;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.entity.Question;
import com.preproject.server.user.entity.User;
import org.junit.jupiter.api.Assertions;
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
public class answerServiceTest {

    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("AnswerService Test")
    void verifyLogic() {
        // Given
        Answer testAnswer = createTestAnswer(1L);
        User testUser = createTestUser(1L);
        Question testQuestion = createTestQuestion(1L);

        given(answerRepository.findById(anyLong())).willReturn(Optional.of(testAnswer));

        Throwable throwable = Assertions.
        // When
        assertThrows(ServiceLogicException.class,
                () -> answerService.createAnswer(testAnswer, 1L, 1L));
    }

    private Answer createTestAnswer(Long answerId) {
        Answer testAnswer = new Answer("답변 내용");
        testAnswer.setAnswerId(answerId);

        return testAnswer;
    }

    private User createTestUser(Long userId) {
        User testUser = new User(
                "test@test.com",
                "1111!",
                "testName",
                false);
        testUser.setUserId(userId);

        return testUser;
    }

    private Question createTestQuestion(Long questionId) {
        Question testQuestion = new Question();
        testQuestion.setTitle("Question");
        testQuestion.setBody("Write Question");

        return testQuestion;
    }
}
