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

    @DynamoDbAttribute("display_name")
    public String getDisplayName() {
        return this.displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }

    @DynamoDbAttribute("user_type")
    public String getUserType() {
        return this.userType;
    }

    public void setUserType(final String userType) {
        this.userType = userType;
    }

    @DynamoDbAttribute("broadcaster_type")
    public String getBroadcasterType() {
        return this.broadcasterType;
    }

    public void setBroadcasterType(final String broadcasterType) {
        this.broadcasterType = broadcasterType;
    }

    @DynamoDbAttribute("description")
    public String getDescription() {
        return this.description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @DynamoDbAttribute("created_at")
    public Long getCreatedAt() {
        return this.createdAt;
    }

    public void setCreatedAt(final Long createdAt) {
        this.createdAt = createdAt;
    }
}
