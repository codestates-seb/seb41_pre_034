package com.preproject.server.user.entity;

import com.preproject.server.answer.entity.Answer;
import com.preproject.server.answer.entity.AnswerComment;
import com.preproject.server.answer.entity.AnswerVote;
import com.preproject.server.constant.LoginType;
import com.preproject.server.constant.UserStatus;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionComment;
import com.preproject.server.question.entity.QuestionVote;
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
}, name = "USERS")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Setter
    @Column(nullable = false)
    private String password;

    @Setter
    @Column(nullable = false, unique = true)
    private String displayName;

    @Setter
    @Column(nullable = false)
    private Boolean emailNotice;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserStatus userStatus;

    @Setter
    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private LoginType loginType;


    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP")
    @CreatedDate
    private LocalDateTime createAt;


    @Column(nullable = false, insertable = false, updatable = false,
            columnDefinition = "datetime default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updateAt;


    /* 연관 관계 */

    @ToString.Exclude
    @OrderBy("questionId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Question> questions = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("answerId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Answer> answers = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("answerVoteId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AnswerVote> answerVotes = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("answerCommentId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<AnswerComment> answerComments = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("questionCommentId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<QuestionComment> questionComments = new ArrayList<>();

    @ToString.Exclude
    @OrderBy("questionVoteId")
    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<QuestionVote> questionVotes = new ArrayList<>();


}