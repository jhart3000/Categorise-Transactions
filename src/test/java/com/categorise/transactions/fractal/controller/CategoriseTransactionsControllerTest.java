package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.model.Transaction;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.categorise.transactions.fractal.helper.Constants.CLIENT_REQUEST;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class CategoriseTransactionsControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldReturnListOfTransactions() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("get-transactions-service-mock.json", Transaction[].class);
    given(service.categoriseTransactions(CLIENT_REQUEST)).willReturn(Arrays.asList(serviceMock));
    mvc.perform(
            get("/banking/2/accounts/fakeAcc62/categoriseTransactions")
                .header("Authorization", "Bearer 1234")
                .header("X-Api-Key", "5678")
                .header("X-Partner-Id", "9101112"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category", Matchers.is("Coffee Purchase")));
  }

  @Test
  void shouldReturnListOfTransactionsWithSameCategory() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject(
            "get-transactions-same-category-service-mock.json", Transaction[].class);
    given(service.getTransactionsWithSameCategory("Amazon Purchase"))
        .willReturn(Arrays.asList(serviceMock));
    mvc.perform(get("/getTransactionsWithSameCategory").header("Category", "Amazon Purchase"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category", Matchers.is("Amazon Purchase")));
  }
}
