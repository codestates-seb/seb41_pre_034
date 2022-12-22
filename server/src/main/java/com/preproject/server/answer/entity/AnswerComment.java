package com.preproject.server.answer.entity;

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
public class AnswerComment {
    /* 생성자 */
    public AnswerComment(
            String comment
    ) {
        this.comment = comment;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerCommentId;

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
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private Answer answer;

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User user;

    public void addAnswer(Answer answer) {
        this.answer = answer;
        answer.addAnswerComment(this);
    }

    public void addUser(User user) {
        this.user = user;
        user.addAnswerComment(this);
    }

}
