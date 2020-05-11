package com.categorise.transactions.service;

import com.categorise.transactions.model.AddCategoryRequest;
import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.mongodb.TransactionDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.categorise.transactions.helper.Constants.ADD_CATEGORY_MESSAGE;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class AddCategoryServiceTest {

  private static final String NEW_CATEGORY = "Supermarket Purchase";

  private AddCategoryService service;

  @Mock private MongoDBInteractionsService mongoDBInterationsService;

  @BeforeEach
  void setUp() {
    service = new AddCategoryService(mongoDBInterationsService);
  }

  @Test
  void shouldUpdateTransaction() throws Exception {
    String[] stringList = {"SAINSBURY'S"};
    AddCategoryRequest request =
        AddCategoryRequest.builder()
            .descriptionSearch(stringList)
            .newCategory(NEW_CATEGORY)
            .build();
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(
            "responses/get-transactions-service-mock.json", TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    MessageResponse response = service.addCategory(request);
    assertThat(response).isNotNull();
    assertThat(response.getMessage()).isEqualTo(ADD_CATEGORY_MESSAGE);
  }
}
