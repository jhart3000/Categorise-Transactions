package com.categorise.transactions.integration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static com.categorise.transactions.helper.Constants.*;
import static com.categorise.transactions.helper.JsonHelper.loadJsonFile;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest
@Import(EmbeddedMongoAutoConfiguration.class)
class IntegrationTest {

  private static final String UPDATE_CATEGORY_REQUEST_2 = "requests/update-category-request-2.json";

  @Autowired private MockMvc mvc;

  @Test
  void shouldReturnListOfTransactionsAfterUpdatingAndAddingCategory() throws Exception {
    mvc.perform(get("/categoriseTransactions/cache/false")).andExpect(status().isOk());

    mvc.perform(get("/getTransactionsWithSameCategory").header("Category", AMAZON_PURCHASE))
        .andExpect(status().isOk())
        .andExpect(
            content()
                .json(loadJsonFile("responses/get-transactions-same-category-service-mock.json")));

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(UPDATE_CATEGORY_REQUEST)))
        .andExpect(status().isOk());

    mvc.perform(
            put("/addCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(ADD_CATEGORY_REQUEST)))
        .andExpect(status().isOk());

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(loadJsonFile(UPDATE_CATEGORY_REQUEST_2)))
        .andExpect(status().isOk());

    mvc.perform(get("/getAllTransactions"))
        .andExpect(status().isOk())
        .andExpect(content().json(loadJsonFile("responses/get-current-list-response.json")));
  }
}
