package com.preproject.server.question.service;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.preproject.server.constant.ErrorCode;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.question.dto.QuestionVotePatchDto;
import com.preproject.server.question.dto.QuestionVotePostDto;
import com.preproject.server.question.entity.QuestionVote;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.repository.QuestionRepository;
import com.preproject.server.question.repository.QuestionVoteRepository;
import com.preproject.server.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.preproject.server.question.entity.QQuestionVote.questionVote;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class QuestionVoteServiceTest {

    @InjectMocks
    private QuestionVoteService questionVoteService;


    @Mock
    private QuestionVoteRepository questionVoteRepository;





    @Test
    void patch() {
        //given
        given(questionVoteRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        Throwable throwable = Assertions.catchThrowable(
                () -> questionVoteService.patch(createQuestionVote(1L),1L));
        //then
        Assertions.assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.NOT_FOUND.getMessage());



    }
    private QuestionVote createQuestionVote(Long questionVoteId){
        QuestionVote questionVote = new QuestionVote();
        questionVote.setVoteStatus(VoteStatus.NONE);
        return questionVote;
    }


}