package com.preproject.server.question.entity;


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

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createAt"),
        @Index(columnList = "updateAt")
})
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
public class QuestionComment {
    /* 생성자 */
    public QuestionComment(
            String comment
    ) {
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionCommentId;

    @Setter
    @Column(nullable = false)
    private String comment;

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
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private User user;

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.EAGER)
    private Question question;

    public void addUser(User user) {
        this.user = user;
        user.addQuestionComment(this);
    }

    public void addQuestion(Question question) {
        this.question = question;
        question.addQuestionComment(this);
    }

}
