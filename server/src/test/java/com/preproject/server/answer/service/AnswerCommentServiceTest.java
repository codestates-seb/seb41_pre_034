package com.preproject.server.answer.service;

import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.repository.AnswerCommentRepository;
import com.preproject.server.answer.service.AnswerCommentService;
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
public class AnswerCommentServiceTest {
    @Mock
    private AnswerCommentRepository answerCommentRepository;

    @InjectMocks
    private AnswerCommentService answerCommentService;

    @Test
    @DisplayName("답변 코멘트 수정 테스트")
    void updateTest() {
        // given
        given(answerCommentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> answerCommentService.updateComment(
                        createTestAnswerComment(1L), 1L));

        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ANSWER_NOT_FOUND.getMessage());
    }

    @Test
    @DisplayName("답변 코멘트 삭제 테스트")
    void deleteTest() {
        // given
        given(answerCommentRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> answerCommentService.delete(1L));

        // Then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.ANSWER_NOT_FOUND.getMessage());
    }

    // 테스트 답변 코멘트 생성
    private AnswerComment createTestAnswerComment(Long answerCommentId) {
        AnswerComment testComment = new AnswerComment();
        testComment.setComment("Create Comment");

        return testComment;
    }
}
