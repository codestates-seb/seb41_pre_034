package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.repository.AnswerRepository;
import com.preproject.server.answer.service.AnswerService;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
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
public class AnswerServiceTest {
    @Mock
    private AnswerRepository answerRepository;

    @InjectMocks
    private AnswerService answerService;

    @Test
    @DisplayName("답변 수정 테스트")
    void updateTest() {
        // given
        given(answerRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> answerService.updateAnswer(createTestAnswer(1L)));

        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ANSWER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("답변 조회 테스트")
    void findTest() {
        // given
        given(answerRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> answerService.findAnswer(1L));

        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ANSWER_NOT_FOUND.getMessage());
    }

    // 테스트 답변 생성
    private Answer createTestAnswer(Long answerId) {
        Answer testAnswer = new Answer();
        testAnswer.setBody("Write Answer");
        testAnswer.setAnswerId(answerId);
        // testAnswer.setCheck(true);

        return testAnswer;
    }
}
