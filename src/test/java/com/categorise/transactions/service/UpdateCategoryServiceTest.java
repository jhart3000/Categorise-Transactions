package com.categorise.transactions.service;

import com.categorise.transactions.model.MessageResponse;
import com.categorise.transactions.mongodb.TransactionDocument;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;

import static com.categorise.transactions.helper.Constants.*;
import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryServiceTest {

  private UpdateCategoryService service;
  @Mock private MongoDBInteractionsService mongoDBInterationsService;

  @BeforeEach
  void setUp() {
    service = new UpdateCategoryService(mongoDBInterationsService);
  }

  @Test
  void shouldUpdateTransaction() throws Exception {
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(SERVICE_MOCK, TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    MessageResponse response =
        service.updateTransaction("0ef942ea-d3ad-4f25-857b-4d4bb7f912d8", UPDATED_CATEGORY);
    Assertions.assertThat(response).isNotNull();
    Assertions.assertThat(response.getMessage()).isEqualTo(UPDATE_CATEGORY_MESSAGE);
  }

  @Test
  void shouldThrowNonExistentTransactionIdForUpdateTransaction() throws Exception {
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(SERVICE_MOCK, TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.updateTransaction("Invalid Id", UPDATED_CATEGORY));
    assertThat(errorResponse).hasMessage("Transaction Id Does Not Exist");
  }
}
