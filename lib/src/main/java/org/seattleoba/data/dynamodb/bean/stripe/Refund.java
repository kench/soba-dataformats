package org.seattleoba.data.dynamodb.bean.stripe;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

/**
 * Key schema of the StripeRefunds table. Items are stored as documents; this bean only describes
 * the primary key and the Charge and PaymentIntent indexes.
 */
@DynamoDbBean
public class Refund {
  private String id;
  private String chargeId;
  private String paymentIntentId;

  @DynamoDbPartitionKey
  @DynamoDbAttribute("id")
  public String getId() {
    return id;
  }

  public void setId(final String id) {
    this.id = id;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "Charge")
  @DynamoDbAttribute("charge")
  public String getChargeId() {
    return chargeId;
  }

  public void setChargeId(final String chargeId) {
    this.chargeId = chargeId;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "PaymentIntent")
  @DynamoDbAttribute("payment_intent")
  public String getPaymentIntentId() {
    return paymentIntentId;
  }

  public void setPaymentIntentId(final String paymentIntentId) {
    this.paymentIntentId = paymentIntentId;
  }
}
