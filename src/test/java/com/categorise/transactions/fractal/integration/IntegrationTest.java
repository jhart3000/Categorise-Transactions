package com.categorise.transactions.fractal.integration;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.ClientResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static com.categorise.transactions.fractal.helper.Constants.*;
import static com.categorise.transactions.fractal.helper.JsonHelper.loadJsonFile;
import static com.categorise.transactions.fractal.helper.JsonHelper.mapJsonFileToObject;
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

  @MockBean private RestTemplate restTemplate;

  @Test
  void shouldReturnListOfTransactionsAfterUpdatingAndAddingCategory() throws Exception {
    ClientResponse clientResponse =
        mapJsonFileToObject(
            "responses/get-transactions-client-response.json", ClientResponse.class);
    Mockito.when(
            restTemplate.exchange(CLIENT_URL, HttpMethod.GET, HTTP_ENTITY, ClientResponse.class))
        .thenReturn(new ResponseEntity<>(clientResponse, HttpStatus.OK));

    mvc.perform(
            get("/banking/2/accounts/fakeAcc62/categoriseTransactions")
                .header("Authorization", "Bearer 1234")
                .header("X-Api-Key", "5678")
                .header("X-Partner-Id", "9101112"))
        .andExpect(status().isOk());

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
