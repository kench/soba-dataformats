package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class TwitchTeamMembership {
    private Integer teamId;
    private Integer userId;

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = "UserId")
    @DynamoDbAttribute("team_id")
    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(final Integer teamId) {
        this.teamId = teamId;
    }

    @DynamoDbSortKey
    @DynamoDbSecondaryPartitionKey(indexNames = "UserId")
    @DynamoDbAttribute("user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(final Integer userId) {
        this.userId = userId;
    }
}
