package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class TwitchAccount {
    private Integer id;
    private String userName;
    private String displayName;
    private String userType;
    private String broadcasterType;
    private String description;
    private Long createdAt;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "UserName")
    @DynamoDbAttribute("user_name")
    public String getUserName() {
        return this.userName;
    }

    public void setUserName(final String userName) {
        this.userName = userName;
    }
}
