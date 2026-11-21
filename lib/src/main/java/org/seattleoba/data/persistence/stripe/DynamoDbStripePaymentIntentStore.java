package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;
import javax.inject.Named;
import org.seattleoba.data.model.stripe.PaymentIntent;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

/** {@link StripePaymentIntentStore} backed by the StripePaymentIntents DynamoDB table. */
public class DynamoDbStripePaymentIntentStore implements StripePaymentIntentStore {
  private static final String CHARGE_INDEX = "Charge";
  private static final String DESCRIPTION_INDEX = "Description";

  private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
  private final ObjectMapper objectMapper;

  /**
   * Creates a store over the given document table.
   *
   * @param dynamoDbTable StripePaymentIntents table with Charge, Customer and Description indexes
   * @param objectMapper Jackson mapper used to convert between JSON documents and records
   */
  @Inject
  public DynamoDbStripePaymentIntentStore(
      @Named("StripePaymentIntents") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
      final ObjectMapper objectMapper) {
    this.dynamoDbTable = dynamoDbTable;
    this.objectMapper = objectMapper;
  }

  @Override
  public PaymentIntent getPaymentIntentById(final String paymentIntentId) {
    final EnhancedDocument enhancedDocument =
        dynamoDbTable.getItem(Key.builder().partitionValue(paymentIntentId).build());
    return getPaymentIntent(enhancedDocument);
  }

  @Override
  public PaymentIntent findPaymentIntentByDescription(final String description) {
    return queryByIndex(DESCRIPTION_INDEX, description);
  }

  @Override
  public PaymentIntent findPaymentIntentByLatestCharge(final String chargeId) {
    return queryByIndex(CHARGE_INDEX, chargeId);
  }

  @Override
  public void updatePaymentIntent(final PaymentIntent paymentIntent) {
    final EnhancedDocument enhancedDocument;
    try {
      enhancedDocument =
          EnhancedDocument.builder().json(objectMapper.writeValueAsString(paymentIntent)).build();
    } catch (final JsonProcessingException exception) {
      throw new IllegalArgumentException(exception);
    }
    dynamoDbTable.putItem(enhancedDocument);
  }

  private PaymentIntent queryByIndex(final String indexName, final String partitionValue) {
    final QueryConditional queryConditional =
        QueryConditional.keyEqualTo(Key.builder().partitionValue(partitionValue).build());
    // A query always returns at least one page, which is empty when nothing matches.
    return dynamoDbTable.index(indexName).query(q -> q.queryConditional(queryConditional)).stream()
        .flatMap(page -> page.items().stream())
        .findFirst()
        .map(this::getPaymentIntent)
        .orElse(null);
  }

  private PaymentIntent getPaymentIntent(final EnhancedDocument enhancedDocument) {
    try {
      return objectMapper.readValue(enhancedDocument.toJson(), PaymentIntent.class);
    } catch (final JsonProcessingException exception) {
      throw new IllegalStateException(exception);
    }
  }
}
