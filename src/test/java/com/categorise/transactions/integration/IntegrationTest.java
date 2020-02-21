package com.categorise.transactions.integration;

import com.categorise.transactions.configuration.BeanDefinitions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static com.categorise.transactions.helper.Constants.*;
import static com.categorise.transactions.helper.JsonHelper.loadJsonFile;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@Import(BeanDefinitions.class)
class IntegrationTest {

  private static final String UPDATE_CATEGORY_REQUEST_2 = "requests/update-category-request-2.json";

  @Autowired private MockMvc mvc;

  @Test
  void shouldReturnListOfTransactionsAfterUpdatingAndAddingCategory() throws Exception {
    mvc.perform(get("/categoriseTransactions")).andExpect(status().isOk());

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
