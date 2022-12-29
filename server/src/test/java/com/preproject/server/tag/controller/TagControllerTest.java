package com.preproject.server.tag.controller;

import com.google.gson.Gson;
import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.entity.QuestionTag;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.repository.QuestionTagRepository;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
import com.preproject.server.tag.mapper.TagMapper;
import com.preproject.server.tag.service.TagService;
import com.preproject.server.user.entity.User;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(TagController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class TagControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private Gson gson;

    @MockBean
    private TagService tagService;

    @MockBean
    private QuestionTagRepository questionTagRepository;

    @MockBean
    private QuestionMapper questionMapper;

    @MockBean
    private TagMapper tagMapper;

    @Test
    @DisplayName("Tag를 포함한 Question 전체 조회 TEST")
    void getQuestionsByTag() throws Exception {
        // Given
        Long tagId = 1L;
        QuestionTag testQuestionTag = createTestQuestionTag();
        QuestionSimpleResponseDto dto =
                createQuestionSimpleResponseDto(testQuestionTag.getQuestion());
        Page<QuestionTag> questionTags =
                new PageImpl<>(
                        List.of(testQuestionTag, testQuestionTag),
                        PageRequest.of(0, 10),
                        2);
        // When

        given(tagService.findTag(anyLong())).willReturn(testQuestionTag.getTag());
        given(questionTagRepository.findAllByTag(any(Tag.class), any(Pageable.class)))
                .willReturn(questionTags);
        given(questionMapper.questionEntityQuestionSimpleResponseDto(any(Question.class)))
                .willReturn(dto);
        RequestBuilder result = RestDocumentationRequestBuilders.get("/tags/" + tagId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(dto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));

    }

    @Test
    @DisplayName("Tag 전체 조회 TEST")
    void getTags() throws Exception {
        // Given
        Tag testTag = createTestTag();
        TagResponseDto dto = createTagResponseDto(testTag);
        Page<Tag> tags =
                new PageImpl<>(
                        List.of(testTag, testTag),
                        PageRequest.of(0, 10),
                        2);
        // When
        given(tagService.findTags(any(Pageable.class))).willReturn(tags);
        given(tagMapper.tagListToTagResponseDtoList(anyList())).willReturn(List.of(dto, dto));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/tags")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].tag").value(dto.getTag()))
                .andExpect(jsonPath("$.data[0].description").value(dto.getDescription()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));
    }

    private Tag createTestTag() {
        Tag tag = new Tag();
        tag.setTag("test");
        tag.setDescription("test");
        return tag;
    }

    private Question createTestQuestion() {
        Question question = new Question();
        User user = new User();
        user.setUserId(1L);
        question.setUser(user);
        question.setBody("testBody");
        question.setTitle("testTitle");
        return question;
    }

    private QuestionTag createTestQuestionTag() {
        QuestionTag questionTag = new QuestionTag();
        Tag testTag = createTestTag();
        Question testQuestion = createTestQuestion();
        questionTag.setTag(testTag);
        questionTag.setQuestion(testQuestion);
        return questionTag;
    }

    private TagResponseDto createTagResponseDto(Tag tag) {
        TagResponseDto dto = new TagResponseDto();
        dto.setTag(tag.getTag());
        dto.setDescription(tag.getDescription());
        return dto;
    }

    private QuestionSimpleResponseDto createQuestionSimpleResponseDto(Question question) {
        QuestionSimpleResponseDto dto = new QuestionSimpleResponseDto();
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        return dto;
    }

}