package org.seattleoba.data.dynamodb.bean.stripe;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class PaymentIntent {
    private String id;
    private String chargeId;
    private String customerId;
    private String description;

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

    @DynamoDbSecondaryPartitionKey(indexNames = "Customer")
    @DynamoDbAttribute("customer")
    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(final String customerId) {
        this.customerId = customerId;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "Description")
    @DynamoDbAttribute("description")
    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }
}
