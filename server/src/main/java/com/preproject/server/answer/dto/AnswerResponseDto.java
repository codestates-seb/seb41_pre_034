package com.preproject.server.answer.dto;


import com.preproject.server.constant.VoteStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class AnswerResponseDto {

    private Long answerId;

    private Long userId;

    private String displayName;

    private String body;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<AnswerCommentResponseDto> answerComments;

    private List<AnswerVoteResponseDto> answerVotes;

    private int countingVote = countingVote();


    private int countingVote() {
        if (this.answerVotes != null) {
            int up = (int) this.answerVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.UP.toString())).count();
            int down = (int) this.answerVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.DOWN.toString())).count();
            return up - down;
        } else {
            return 0;
        }
    }
}
