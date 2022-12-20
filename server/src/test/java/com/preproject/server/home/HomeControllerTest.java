package com.preproject.server.home;

import com.preproject.server.util.ApiDocumentUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.PayloadDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
class HomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test() throws Exception {
        // Given

        // When
        ResultActions actions = mockMvc.perform(
                RestDocumentationRequestBuilders
                        .get("/")
                        .accept(MediaType.APPLICATION_JSON)
        );
        // Then

        actions
                .andExpect(status().isOk())
                .andDo(
                        MockMvcRestDocumentation.document("get-home",
                                ApiDocumentUtils.getRequestPreProcessor(),
                                ApiDocumentUtils.getResponsePreProcessor(),
                                PayloadDocumentation.responseFields(
                                        List.of(
                                                fieldWithPath("hello").type(JsonFieldType.STRING).description("test")
                                        )
                                )));
    }


}