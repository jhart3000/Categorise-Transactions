package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.AddCategoryRequest;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.categorise.transactions.fractal.helper.Constants.ADD_CATEGORY;
import static com.categorise.transactions.fractal.helper.Constants.INVALID_BODY;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
class AddCategoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldThrowInvalidRequestBody() throws Exception {

    mvc.perform(put("/addCategory").contentType(APPLICATION_JSON).content(INVALID_BODY))
        .andExpect(status().isBadRequest())
        .andExpect(
            jsonPath(
                "$.message",
                Matchers.is(
                    "Invalid Request Body: Please pass getDescriptionSearch and getNewCategory in request body")));
  }

  @Test
  void shouldAddCategory() throws Exception {

    doNothing().when(service).addCategory(any(AddCategoryRequest.class));

    mvc.perform(put("/addCategory").contentType(APPLICATION_JSON).content(ADD_CATEGORY))
        .andExpect(status().isOk());
  }
}
