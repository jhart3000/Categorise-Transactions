package com.categorise.transactions.controller;

import com.categorise.transactions.model.AddCategoryRequest;
import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.service.AddCategoryService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.categorise.transactions.helper.Constants.*;
import static com.categorise.transactions.helper.JsonHelper.loadJsonFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class AddCategoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private AddCategoryService service;

  @Test
  void shouldThrowInvalidRequestBody() throws Exception {

    mvc.perform(
            put("/addCategory").contentType(APPLICATION_JSON).content(loadJsonFile(INVALID_BODY)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message",
                Matchers.is(
                    "Invalid Request Body: Please pass getDescriptionSearch and getNewCategory in request body")));
  }

  @Test
  void shouldAddCategory() throws Exception {

    given(service.addCategory(any(AddCategoryRequest.class)))
        .willReturn(MessageResponse.builder().message(ADD_CATEGORY_MESSAGE).build());

    mvc.perform(
            put("/addCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(ADD_CATEGORY_REQUEST)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Matchers.is(ADD_CATEGORY_MESSAGE)));
  }
}
