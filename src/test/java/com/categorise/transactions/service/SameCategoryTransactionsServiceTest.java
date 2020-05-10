package com.categorise.transactions.service;

import com.categorise.transactions.helper.Constants;
import com.categorise.transactions.mongodb.TransactionDocument;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static com.categorise.transactions.helper.JsonHelper.mapJsonFileToObject;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SameCategoryTransactionsServiceTest {

  private SameCategoryTransactionService service;
  @Mock private MongoDBInteractionsService mongoDBInterationsService;

  @BeforeEach
  void setUp() {
    service = new SameCategoryTransactionService(mongoDBInterationsService);
  }

  @Test
  void shouldReturnTransactionWithTheSameCategory() throws Exception {
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(Constants.SERVICE_MOCK, TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    List<TransactionDocument> response =
        service.getTransactionsWithSameCategory(Constants.COFFEE_PURCHASE);
    assertThat(response).isNotNull();
    assertThat(response.size()).isEqualTo(3);
  }

  @Test
  void shouldThrowCategoryDoesNotExistForTransactionWithTheSameCategory() throws Exception {
    TransactionDocument[] serviceMock =
        mapJsonFileToObject(Constants.SERVICE_MOCK, TransactionDocument[].class);
    given(mongoDBInterationsService.getAllFromMongoDB()).willReturn(Arrays.asList(serviceMock));
    Throwable errorResponse =
        catchThrowable(() -> service.getTransactionsWithSameCategory("Unknown Category"));
    assertThat(errorResponse).hasMessage("Category Does Not Exist");
  }
}
