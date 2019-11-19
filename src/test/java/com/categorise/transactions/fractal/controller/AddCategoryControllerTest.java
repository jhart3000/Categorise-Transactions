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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
public class AddCategoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldThrowInvalidRequestBody() throws Exception {
    String body =
        "{    \n"
            + "\t\"invalid\": \"0ef942ea-d3ad-4f25-857b-4d4bb7f912d8\",    \n"
            + "    \"invalid2\": \"Updated Category\"\n"
            + "} ";

    mvc.perform(put("/addCategory").contentType(APPLICATION_JSON).content(body))
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

    String body =
        "{    \n"
            + "\t\"descriptionSearch\": [\"BT\", \"Mobile\"],    \n"
            + "    \"newCategory\": \"Added Category\"\n"
            + "} ";

    mvc.perform(put("/addCategory").contentType(APPLICATION_JSON).content(body))
        .andExpect(status().isOk());
  }
}
