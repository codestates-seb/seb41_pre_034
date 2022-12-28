package com.preproject.server.answer;

import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.answer.repository.AnswerVoteRepository;
import com.preproject.server.answer.service.AnswerVoteService;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
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
public class AnswerVoteServiceTest {
    @Mock
    private AnswerVoteRepository answerVoteRepository;

    @InjectMocks
    private AnswerVoteService answerVoteService;

    @Test
    @DisplayName("답변 추천 수정 테스트")
    void updateTest() {
        // given
        given(answerVoteRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        Throwable throwable = Assertions.catchThrowable(
                () -> answerVoteService.updateVote(createTestAnswerVote(1L), 1L));

        // then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getMessage());
    }

    // 테스트 답변 추천 생성
    private AnswerVote createTestAnswerVote(Long answerVoteId) {
        AnswerVote testVote = new AnswerVote();
        testVote.setVoteStatus(VoteStatus.NONE);

        return testVote;
    }
}
