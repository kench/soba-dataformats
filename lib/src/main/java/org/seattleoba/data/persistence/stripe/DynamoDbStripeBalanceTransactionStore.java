package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.inject.Named;
import org.seattleoba.data.model.stripe.BalanceTransaction;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

/** {@link StripeBalanceTransactionStore} backed by the StripeBalanceTransactions DynamoDB table. */
public class DynamoDbStripeBalanceTransactionStore implements StripeBalanceTransactionStore {
  private static final String SOURCE_INDEX = "Source";

  private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
  private final ObjectMapper objectMapper;

  /**
   * Creates a store over the given document table.
   *
   * @param dynamoDbTable StripeBalanceTransactions table with a Source index
   * @param objectMapper Jackson mapper used to convert between JSON documents and records
   */
  @Inject
  public DynamoDbStripeBalanceTransactionStore(
      @Named("StripeBalanceTransactions") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
      final ObjectMapper objectMapper) {
    this.dynamoDbTable = dynamoDbTable;
    this.objectMapper = objectMapper;
  }

  @Override
  public BalanceTransaction getBalanceTransactionById(final String balanceTransactionId) {
    final EnhancedDocument enhancedDocument =
        dynamoDbTable.getItem(Key.builder().partitionValue(balanceTransactionId).build());
    return getBalanceTransaction(enhancedDocument);
  }

  @Override
  public BalanceTransaction getBalanceTransactionBySourceId(final String sourceId) {
    final QueryConditional queryConditional =
        QueryConditional.keyEqualTo(Key.builder().partitionValue(sourceId).build());
    // A query always returns at least one page, which is empty when nothing matches.
    return dynamoDbTable
        .index(SOURCE_INDEX)
        .query(q -> q.queryConditional(queryConditional))
        .stream()
        .flatMap(page -> page.items().stream())
        .findFirst()
        .map(this::getBalanceTransaction)
        .orElse(null);
  }

  @Override
  public void updateBalanceTransaction(final BalanceTransaction balanceTransaction) {
    final EnhancedDocument enhancedDocument;
    try {
      enhancedDocument =
          EnhancedDocument.builder()
              .json(objectMapper.writeValueAsString(balanceTransaction))
              .build();
    } catch (final JsonProcessingException exception) {
      throw new IllegalArgumentException(exception);
    }
    dynamoDbTable.putItem(enhancedDocument);
  }

  private BalanceTransaction getBalanceTransaction(final EnhancedDocument enhancedDocument) {
    try {
      return objectMapper.readValue(enhancedDocument.toJson(), BalanceTransaction.class);
    } catch (final JsonProcessingException exception) {
      throw new IllegalStateException(exception);
    }
  }
}
