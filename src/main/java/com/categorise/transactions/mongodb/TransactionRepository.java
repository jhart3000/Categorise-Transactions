package com.categorise.transactions.mongodb;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface TransactionRepository
    extends ReactiveMongoRepository<TransactionDocument, String> {}
