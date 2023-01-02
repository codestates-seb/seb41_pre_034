package com.preproject.server.question.entity;


import com.preproject.server.tag.entity.Tag;
import lombok.*;
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
@AllArgsConstructor
public class QuestionTag {

    public QuestionTag(
            Question question,
            Tag tag
    ) {
        addTag(tag);
        addQuestion(question);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionTagId;

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
    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Question question;

    @Setter
    @ManyToOne(optional = true, fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private Tag tag;

    public void addQuestion(Question question) {
        this.question = question;
        question.addQuestionTag(this);
    }

    public void addTag(Tag tag) {
        this.tag = tag;
        tag.addQuestionTag(this);
    }

}
