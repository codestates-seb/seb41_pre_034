package com.preproject.server.question.dto;

import com.preproject.server.answer.dto.AnswerResponseDto;
import com.preproject.server.constant.VoteStatus;
import com.preproject.server.tag.dto.TagResponseDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class QuestionResponseDto {

    private Long questionId;

    private Long userId;

    private String displayName;

    private String title;

    private String body;

    private int viewCounting;

    private String questionStatus;

    private LocalDateTime createAt;

    private LocalDateTime updateAt;

    private List<QuestionVoteResponseDto> questionVotes;

    private int countingVote = countingVote();
    private List<AnswerResponseDto> answers;

    private int countingAnswers = answers.size();

    private List<QuestionCommentResponseDto> questionComments;

    private List<TagResponseDto> tags;

    private int countingVote() {
        if (this.questionVotes != null) {
            int up = (int) this.questionVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.UP.toString())).count();
            int down = (int) this.questionVotes.stream()
                    .filter(dto -> dto.getVoteStatus().equals(VoteStatus.DOWN.toString())).count();
            return up - down;
        } else {
            return 0;
        }
    }

}
