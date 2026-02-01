package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class TwitchTeam {
    private String displayName;
    private Integer id;
    private String name;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Integer getId() {
        return id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "TeamName")
    @DynamoDbAttribute("name")
    public String getName() { return name; }

    @DynamoDbAttribute("display_name")
    public String getDisplayName() { return displayName; }
}
