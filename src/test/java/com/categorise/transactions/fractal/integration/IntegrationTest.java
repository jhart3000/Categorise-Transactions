package com.categorise.transactions.fractal.integration;

import com.categorise.transactions.fractal.configuration.BeanDefinitions;
import com.categorise.transactions.fractal.model.ClientResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
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

  private HttpEntity<?> entity;

  @Autowired private MockMvc mvc;

  @MockBean private RestTemplate restTemplate;

  @BeforeEach
  void setup() {
    HttpHeaders headers = new HttpHeaders();
    headers.set("Authorization", "Bearer 1234");
    headers.set("X-Api-Key", "5678");
    headers.set("X-Partner-Id", "9101112");
    entity = new HttpEntity<>(headers);
  }

  @Test
  void shouldReturnListOfTransactionsAfterUpdatingAndAddingCategory() throws Exception {
    ClientResponse clientResponse =
        mapJsonFileToObject("get-transactions-client-response.json", ClientResponse.class);
    Mockito.when(restTemplate.exchange(CLIENT_URL, HttpMethod.GET, entity, ClientResponse.class))
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
            content().json(loadJsonFile("get-transactions-same-category-service-mock.json")));

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(UPDATE_CATEGORY_REQUEST))
        .andExpect(status().isOk());

    mvc.perform(put("/addCategory").contentType(APPLICATION_JSON).content(ADD_CATEGORY))
        .andExpect(status().isOk());

    String updateCategoryBody2 =
        "{    \n"
            + "\t\"transactionId\": \"f73921e2-8dd3-48fc-8096-cf7b8f10d5b7\",    \n"
            + "    \"category\": \"Not Categorised\"\n"
            + "} ";

    mvc.perform(
            put("/updateTransactionCategory")
                .contentType(APPLICATION_JSON)
                .content(updateCategoryBody2))
        .andExpect(status().isOk());

    mvc.perform(get("/getAllTransactions"))
        .andExpect(status().isOk())
        .andExpect(content().json(loadJsonFile("get-current-list-response.json")));
  }
}
