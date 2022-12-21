package com.preproject.server.tag.entity;


import com.preproject.server.question.entity.QuestionTag;
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
}, name = "TAGS")
@EntityListeners(AuditingEntityListener.class)
@Entity
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Setter
    @Column(nullable = false)
    private String tag;

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
    @OrderBy("questionTagId")
    @OneToMany(mappedBy = "tag", cascade = CascadeType.REMOVE)
    private List<QuestionTag> questionTags = new ArrayList<>();

    public void addQuestionTag(QuestionTag questionTag) {
        questionTags.add(questionTag);
    }

}
