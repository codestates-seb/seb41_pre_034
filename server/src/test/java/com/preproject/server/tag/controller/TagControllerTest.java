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
import com.preproject.server.util.ApiDocumentUtils;
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
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
    @DisplayName("Tag를 포함한 Question 전체 조회 Controller TEST")
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
        RequestBuilder result = RestDocumentationRequestBuilders.get("/tags/{tagId}?page=0" , tagId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].title").value(dto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(dto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(MockMvcRestDocumentation.document("getQuestionByTag",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("page").description("요청 페이지(0부터 1페이지)")
                        ),
                        RequestDocumentation.pathParameters(
                                parameterWithName("tagId").description("태그 식별자")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].questionId").type(JsonFieldType.NUMBER).description("질문 식별자"),
                                        fieldWithPath("data[].userId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].displayName").type(JsonFieldType.STRING).description("닉네임"),
                                        fieldWithPath("data[].title").type(JsonFieldType.STRING).description("질문의 Title"),
                                        fieldWithPath("data[].body").type(JsonFieldType.STRING).description("질문의 Body"),
                                        fieldWithPath("data[].questionStatus").type(JsonFieldType.STRING).description("질문의 답변 채택 여부"),
                                        fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("data[].updateAt").type(JsonFieldType.STRING).description("최종 수정 시각"),
                                        fieldWithPath("data[].countingVote").type(JsonFieldType.NUMBER).description("추천 수"),
                                        fieldWithPath("data[].viewCounting").type(JsonFieldType.NUMBER).description("조회 수"),
                                        fieldWithPath("data[].answerCounting").type(JsonFieldType.NUMBER).description("질문의 답변 수"),
                                        fieldWithPath("data[].tags").type(JsonFieldType.ARRAY).description("질문이 소유하고 있는 태그 리스트"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")
                                )
                        )
                ));

    }

    @Test
    @DisplayName("Tag 전체 조회 Controller TEST")
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
        RequestBuilder result = RestDocumentationRequestBuilders.get("/tags?page=0")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].tag").value(dto.getTag()))
                .andExpect(jsonPath("$.data[0].description").value(dto.getDescription()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(MockMvcRestDocumentation.document("getTags",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("page").description("요청 페이지(0부터 1페이지)")
                        ),
                        PayloadDocumentation.responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].tagId").type(JsonFieldType.NUMBER).description("태그 식별자"),
                                        fieldWithPath("data[].tag").type(JsonFieldType.STRING).description("태그"),
                                        fieldWithPath("data[].description").type(JsonFieldType.STRING).description("태그 상세 내용"),
                                        fieldWithPath("data[].createAt").type(JsonFieldType.STRING).description("생성 시각"),
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("요청 페이지 정보"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("요청 페이지"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("페이지당 요청 회원"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 멤버"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("생성된 총 페이지")
                                )
                        )
                ));
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
        dto.setTagId(1L);
        dto.setCreateAt(LocalDateTime.now());
        dto.setTag(tag.getTag());
        dto.setDescription(tag.getDescription());
        return dto;
    }

    private QuestionSimpleResponseDto createQuestionSimpleResponseDto(Question question) {
        QuestionSimpleResponseDto dto = new QuestionSimpleResponseDto();
        dto.setQuestionId(1L);
        dto.setQuestionStatus("OPENED");
        dto.setDisplayName("testUesr");
        dto.setCreateAt(LocalDateTime.now());
        dto.setUpdateAt(LocalDateTime.now());
        dto.setBody(question.getBody());
        dto.setTitle(question.getTitle());
        dto.setUserId(question.getUser().getUserId());
        dto.setTags(List.of("java","test"));
        return dto;
    }

}