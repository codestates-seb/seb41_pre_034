package com.preproject.server.answer.entity;

import com.preproject.server.question.entity.Question;
import com.preproject.server.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createAt"),
        @Index(columnList = "updateAt")
},name="ANSWERS")
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
public class Answer {
    /* 생성자 */
    public Answer(
            String body
    ) {
        this.body = body;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter
    private Long answerId;

    @Setter
    @Column(nullable = false, columnDefinition = "LONGTEXT")
    private String body;

    @Setter
    @Column(name = "checked",nullable = false)
    private Boolean check;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createAt;

    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updateAt;

    @Column(nullable = false)
    @Setter
    private int countingVote;

    /* 연관 관계 */

    @ToString.Exclude
    @OrderBy("answerCommentId")
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private Set<AnswerComment> answerComments = new LinkedHashSet<>();

    @ToString.Exclude
    @OrderBy("answerVoteId")
    @OneToMany(mappedBy = "answer", cascade = CascadeType.REMOVE)
    private Set<AnswerVote> answerVotes = new LinkedHashSet<>();

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Question question;

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private User user;

    public void addAnswerComment(AnswerComment answerComment) {
        answerComments.add(answerComment);
    }

    public void addAnswerVote(AnswerVote answerVote) {
        answerVotes.add(answerVote);
    }

    public void addQuestion(Question question) {
        this.question = question;
        question.addAnswer(this);
    }

    public void addUser(User user) {
        this.user = user;
        user.addAnswer(this);
    }

}
