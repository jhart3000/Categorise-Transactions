package com.categorise.transactions.controller;

import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.service.UpdateCategoryService;
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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class UpdateCategoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private UpdateCategoryService service;

  @Test
  void shouldUpdateCategory() throws Exception {
    given(service.updateTransaction(anyString(), anyString()))
        .willReturn(MessageResponse.builder().message(UPDATE_CATEGORY_MESSAGE).build());

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(UPDATE_CATEGORY_REQUEST)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message", Matchers.is(UPDATE_CATEGORY_MESSAGE)));
  }

  @Test
  void shouldThrowInvalidRequestBody() throws Exception {

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(INVALID_BODY)))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message",
                Matchers.is(
                    "Invalid Request Body: Please pass transactionId and category in request body")));
  }
}
