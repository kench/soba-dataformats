package org.seattleoba.data.dynamodb.bean.stripe;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class Charge {
    private String id;
    private String customerId;
    private String paymentIntentId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "Customer")
    @DynamoDbAttribute("customer")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
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
