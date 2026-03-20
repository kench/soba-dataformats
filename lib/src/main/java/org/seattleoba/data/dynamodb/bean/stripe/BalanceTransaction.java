package org.seattleoba.data.dynamodb.bean.stripe;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class BalanceTransaction {
    private String id;
    private String sourceId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "Source")
    @DynamoDbAttribute("source")
    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(final String sourceId) {
        this.sourceId = sourceId;
    }
}
