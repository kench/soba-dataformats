package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.extensions.annotations.DynamoDbVersionAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

import java.math.BigDecimal;

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

    @DynamoDbAttribute(value = "purchaser_name")
    public String getPurchaserName() {
        return purchaserName;
    }

    @DynamoDbAttribute(value = "amount")
    public BigDecimal getAmount() {
        return amount;
    }

    @DynamoDbAttribute(value = "currency")
    public String getCurrency() {
        return currency;
    }

    @DynamoDbAttribute(value = "customer")
    public String getCustomerId() {
        return customerId;
    }

    @DynamoDbAttribute(value = "payment_intent")
    public String getPaymentIntentId() {
        return paymentIntentId;
    }

    @DynamoDbAttribute(value = "twitch_user")
    public Integer getTwitchUserId() {
        return twitchUserId;
    }

    @DynamoDbVersionAttribute
    @DynamoDbAttribute(value = "version")
    public Integer getVersion() {
        return version;
    }
}
