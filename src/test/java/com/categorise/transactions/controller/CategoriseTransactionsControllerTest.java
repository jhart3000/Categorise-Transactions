package com.categorise.transactions.controller;

import com.categorise.transactions.configuration.BeanDefinitions;
import com.categorise.transactions.model.Transaction;
import com.categorise.transactions.service.CategoriseTransactionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.categorise.transactions.helper.Constants.COFFEE_PURCHASE;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
class CategoriseTransactionsControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private CategoriseTransactionsService service;

  @Test
  void shouldReturnListOfTransactions() throws Exception {
    Transaction[] serviceMock =
        mapJsonFileToObject("responses/get-transactions-service-mock.json", Transaction[].class);
    given(service.categoriseTransactions()).willReturn(Arrays.asList(serviceMock));
    mvc.perform(get("/categoriseTransactions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category", Matchers.is(COFFEE_PURCHASE)));
  }
}
