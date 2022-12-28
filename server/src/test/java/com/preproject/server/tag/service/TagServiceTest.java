package com.preproject.server.tag.service;

import com.preproject.server.constant.ErrorCode;
import com.preproject.server.exception.ServiceLogicException;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.repository.TagRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @InjectMocks
    private TagService tagService;

    @Test
    @DisplayName("CreateTagByString 메소드 검증 TEST")
    void createTagByString() {
        // Given
        String tags = "java, spring, javascript";
        // When
        given(tagRepository.findByTag(anyString())).willReturn(Optional.empty());
        given(tagRepository.save(any(Tag.class))).willReturn(new Tag("java",""));
        List<Tag> tagByString = tagService.createTagByString(tags);
        // Then
        assertThat(tagByString.size()).isEqualTo(3);
        assertThat(tagByString.stream().findFirst().get().getTag()).isEqualTo("java");
    }

    @Test
    @DisplayName("Tag Service 검증 로직 TEST")
    void verifyTagByTagId() {
        // Given
        Tag testTag = new Tag("java", "");
        // When
        given(tagRepository.findById(anyLong())).willReturn(Optional.empty());
        Throwable throwable = Assertions.catchThrowable(() -> tagService.findTag(1L));
        // Then
        assertThat(throwable)
                .isInstanceOf(ServiceLogicException.class)
                .hasMessageContaining(ErrorCode.TAG_NOT_FOUND.getMessage());
    }

}