package com.preproject.server.tag.entity;


import com.preproject.server.question.entity.QuestionTag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@ToString
@Table(indexes = {
        @Index(columnList = "createAt"),
        @Index(columnList = "updateAt")
}, name = "TAGS")
@EntityListeners(AuditingEntityListener.class)
@Entity
@NoArgsConstructor
public class Tag {

    /* 생성자 */
    public Tag(
            String tag,
            String description
    ) {
        this.tag = tag;
        this.description = description;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    @Setter
    @Column(nullable = false, unique = true)
    private String tag;

    @Setter
    @Column(nullable = true, length = 1000)
    private String description;

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
    @OneToMany(mappedBy = "tag", cascade = CascadeType.ALL)
    private List<QuestionTag> questionTags = new ArrayList<>();

    public void addQuestionTag(QuestionTag questionTag) {
        questionTags.add(questionTag);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        return tagId != null && tagId.equals(((Tag) obj).getTagId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, description, createAt, updateAt);
    }

}
