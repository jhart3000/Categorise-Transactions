package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.doNothing;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
class UpdateCategoryControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldUpdateCategory() throws Exception {
    doNothing()
        .when(service)
        .updateTransaction("0ef942ea-d3ad-4f25-857b-4d4bb7f912d8", "Updated Category");

    String body =
        "{    \n"
            + "\t\"transactionId\": \"0ef942ea-d3ad-4f25-857b-4d4bb7f912d8\",    \n"
            + "    \"category\": \"Updated Category\"\n"
            + "} ";

    mvc.perform(put("/updateTransactionCategory").contentType(APPLICATION_JSON).content(body))
        .andExpect(status().isOk());
  }
}
