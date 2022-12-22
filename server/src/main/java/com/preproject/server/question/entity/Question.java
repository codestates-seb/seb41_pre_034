package com.preproject.server.question.entity;


import com.preproject.server.answer.entity.Answer;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.user.entity.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createAt"),
        @Index(columnList = "updateAt")
},name="QUESTIONS")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @Setter
    @Column(nullable = false)
    private String title;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Setter
    @Enumerated(value = EnumType.STRING)
    @Column
    private QuestionStatus questionStatus = QuestionStatus.OPENED;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updateAt;

    /* 연관 관계 */

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;

    @ToString.Exclude
    @OrderBy("answerId")
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("questionCommentId")
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionComment> questionComments = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("questionVoteId")
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionVote> questionVotes = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("questionTagId")
    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<QuestionTag> questionTags = new ArrayList<>();

    public void addUser(User user) {
        this.user = user;
        user.addQuestion(this);
    }

    public void addAnswer(Answer answer) {
        answers.add(answer);
    }

    public void addQuestionComment(QuestionComment questionComment) {
        questionComments.add(questionComment);
    }

    public void addQuestionVote(QuestionVote questionVote) {
        questionVotes.add(questionVote);
    }

    public void addQuestionTag(QuestionTag questionTag) {
        questionTags.add(questionTag);
    }


}