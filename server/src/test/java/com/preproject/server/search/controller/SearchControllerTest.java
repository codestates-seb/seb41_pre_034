package com.preproject.server.search.controller;

import com.preproject.server.config.auth.SecurityConfig;
import com.preproject.server.question.dto.QuestionSimpleDto;
import com.preproject.server.question.dto.QuestionSimpleResponseDto;
import com.preproject.server.question.mapper.QuestionMapper;
import com.preproject.server.question.service.QuestionService;
import com.preproject.server.tag.dto.TagResponseDto;
import com.preproject.server.tag.service.TagService;
import com.preproject.server.util.ApiDocumentUtils;
import com.preproject.server.utils.JwtAuthorityUtils;
import com.preproject.server.utils.JwtTokenizer;
import com.preproject.server.utils.TestStub;
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
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.restdocs.request.RequestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
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
        QuestionSimpleDto dto = TestStub.createQuestSimpleDto(TestStub.createTestQuestion());
        QuestionSimpleResponseDto responseDto = TestStub.createQuestionSimpleResponseDto(TestStub.createTestQuestion());
        // When
        given(questionService.findAllByParam(anyMap(), any(Pageable.class)))
                .willReturn(new PageImpl<>(List.of(dto, dto), PageRequest.of(0, 10), 2));
        given(questionMapper.questionDtoListToSimpleResponseDtoList(anyList()))
                .willReturn(List.of(responseDto, responseDto));
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/search?page=0&displayName=test&keyWord=test&tag=java")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].userId").value(responseDto.getUserId()))
                .andExpect(jsonPath("$.data[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.data[0].body").value(responseDto.getBody()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(MockMvcRestDocumentation.document("getSearchParam",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("page").description("요청 페이지(0부터 1페이지)"),
                                parameterWithName("keyWord").description("검색 키워드(질문 제목과 내용에 키워드가 포함된 질문 모두 조회)"),
                                parameterWithName("tag").description("해당 태그를 포함한 질문 전체 조회"),
                                parameterWithName("displayName").description("닉네임의 사용자가 작성한 질문을 조회")

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
    @DisplayName("Tag 검색 Controller 동작 TEST")
    void searchTag() throws Exception {
        // Given
        TagResponseDto responseDto = TestStub.createTagResponseDto();
        // When
        given(tagService.findTags(any(Pageable.class), anyMap()))
                .willReturn(new PageImpl<>(
                        List.of(responseDto, responseDto),
                        PageRequest.of(0, 10),
                        2));
        RequestBuilder result = RestDocumentationRequestBuilders
                .get("/search/tag?page=0&tag=java")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .characterEncoding(StandardCharsets.UTF_8.displayName());
        // Then
        mockMvc.perform(result)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].tag").value(responseDto.getTag()))
                .andExpect(jsonPath("$.data[0].description").value(responseDto.getDescription()))
                .andExpect(jsonPath("$.pageInfo.page").value(1))
                .andExpect(jsonPath("$.pageInfo.totalElements").value(2))
                .andDo(MockMvcRestDocumentation.document("getSearchTag",
                        ApiDocumentUtils.getRequestPreProcessor(),
                        ApiDocumentUtils.getResponsePreProcessor(),
                        RequestDocumentation.requestParameters(
                                parameterWithName("page").description("요청 페이지(0부터 1페이지)"),
                                parameterWithName("tag").description("해당 태그를 포함한 질문 전체 조회")
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
}