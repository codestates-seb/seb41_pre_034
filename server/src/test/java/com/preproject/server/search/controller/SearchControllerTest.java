package com.preproject.server.search.controller;

import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.constant.QuestionStatus;
import com.preproject.server.question.dto.QuestionSimpleDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.entity.Question;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.entity.Tag;
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


@WebMvcTest(SearchController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
@Import({JwtAuthorityUtils.class, JwtTokenizer.class, SecurityConfig.class})
class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TagService tagService;

    @MockBean
    private QuestionService questionService;

    @MockBean
    private QuestionMapper questionMapper;

    @Test
    @DisplayName("질문 검색 Controller 동작 TEST")
    void search() throws Exception {
        // Given
        QuestionSimpleDto dto = createQuestSimpleDto(createTestQuestion());
        QuestionSimpleResponseDto responseDto = createQuestionSimpleResponseDto(createTestQuestion());
        // When
        given(questionService.findAllByParam(anyMap(), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(dto, dto), PageRequest.of(0, 10), 2));
        given(questionMapper.questionDtoListToSimpleResponseDtoList(anyList()))
                .willReturn(List.of(responseDto, responseDto));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/search")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(responseDto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));
    }

    @Test
    @DisplayName("Tag 검색 Controller 동작 TEST")
    void searchTag() throws Exception {
        // Given
        TagResponseDto responseDto = createTagResponseDto(createTestTag());
        // When
        given(tagService.findTags(any(Pageable.class), anyMap()))
                .willReturn(new PageImpl<>(
                        List.of(responseDto, responseDto),
                        PageRequest.of(0, 10),
                        2));
        RequestBuilder result = RestDocumentationRequestBuilders.get("/search/tag")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName())
                .with(csrf());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].tag").value(responseDto.getTag()))
                .andExpect(jsonPath("$.data[0].description").value(responseDto.getDescription()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2));
    }

    private Question createTestQuestion() {
        Question question = new Question();
        User user = new User();
        user.setUserId(1L);
        user.setDisplayName("testUser");
        question.setUser(user);
        question.setBody("testBody");
        question.setTitle("testTitle");
        question.setQuestionStatus(QuestionStatus.OPENED);
        return question;
    }

    private QuestionSimpleResponseDto createQuestionSimpleResponseDto(Question question) {
        QuestionSimpleResponseDto dto = new QuestionSimpleResponseDto();
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        return dto;
    }

    private QuestionSimpleDto createQuestSimpleDto(Question question) {
        QuestionSimpleDto dto = new QuestionSimpleDto();
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        return dto;
    }

    private TagResponseDto createTagResponseDto(Tag tag) {
        TagResponseDto dto = new TagResponseDto();
        dto.setTag(tag.getTag());
        dto.setDescription(tag.getDescription());
        return dto;
    }

    private Tag createTestTag() {
        Tag tag = new Tag();
        tag.setTag("test");
        tag.setDescription("test");
        return tag;
    }


}