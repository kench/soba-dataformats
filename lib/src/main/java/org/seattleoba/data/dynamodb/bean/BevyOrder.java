package org.seattleoba.data.dynamodb.bean;

import java.math.BigDecimal;
import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/** DynamoDB item describing a Bevy order and the Stripe payment linked to it. */
@DynamoDbBean
public class BevyOrder {
  private String orderId;
  private String purchaserName;
  private BigDecimal amount;
  private String currency;
  private String customerId;
  private String paymentIntentId;
  private Integer twitchUserId;
  private Integer version;

  @DynamoDbAttribute(value = "order_id")
  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(final String orderId) {
    this.orderId = orderId;
  }

  @DynamoDbAttribute(value = "purchaser_name")
  public String getPurchaserName() {
    return purchaserName;
  }

  public void setPurchaserName(final String purchaserName) {
    this.purchaserName = purchaserName;
  }

  @DynamoDbAttribute(value = "amount")
  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(final BigDecimal amount) {
    this.amount = amount;
  }

  @DynamoDbAttribute(value = "currency")
  public String getCurrency() {
    return currency;
  }

  public void setCurrency(final String currency) {
    this.currency = currency;
  }

  @DynamoDbAttribute(value = "customer")
  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(final String customerId) {
    this.customerId = customerId;
  }

  @DynamoDbAttribute(value = "payment_intent")
  public String getPaymentIntentId() {
    return paymentIntentId;
  }

  public void setPaymentIntentId(final String paymentIntentId) {
    this.paymentIntentId = paymentIntentId;
  }

  @DynamoDbAttribute(value = "twitch_user")
  public Integer getTwitchUserId() {
    return twitchUserId;
  }

  public void setTwitchUserId(final Integer twitchUserId) {
    this.twitchUserId = twitchUserId;
  }

  @DynamoDbVersionAttribute
  @DynamoDbAttribute(value = "version")
  public Integer getVersion() {
    return version;
  }

  public void setVersion(final Integer version) {
    this.version = version;
  }
}
