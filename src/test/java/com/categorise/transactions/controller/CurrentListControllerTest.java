package com.categorise.transactions.controller;

import com.categorise.transactions.mongodb.TransactionDocument;
import com.categorise.transactions.service.MongoDBInteractionsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static com.categorise.transactions.helper.Constants.AMAZON_PURCHASE;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
class CurrentListControllerTest {

  @Autowired private MockMvc mvc;

  @MockBean private MongoDBInteractionsService service;

  @Test
  void shouldReturnCurrentTransactionList() throws Exception {
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(
            "responses/get-transactions-same-category-service-mock.json",
            TransactionDocument[].class);
    given(service.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    mvc.perform(get("/getAllTransactions"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].category", Matchers.is(AMAZON_PURCHASE)));
  }
}
