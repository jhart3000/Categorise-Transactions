package com.categorise.transactions.fractal.controller;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.Transaction;
import com.categorise.transactions.fractal.service.CategoriseTransactionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.categorise.transactions.fractal.helper.Constants.AMAZON_PURCHASE;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
class CurrentListControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldReturnCurrentTransactionList() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject(
            "responses/get-transactions-same-category-service-mock.json", Transaction[].class);
    given(service.returnCurrentTransactionList()).willReturn(Arrays.asList(serviceMock));
    mvc.perform(get("/getAllTransactions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category", Matchers.is(AMAZON_PURCHASE)));
  }
}
