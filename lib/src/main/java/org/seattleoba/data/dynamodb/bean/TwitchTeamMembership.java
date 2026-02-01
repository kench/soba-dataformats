package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@DynamoDbBean
public class TwitchTeamMembership {
    private Integer teamId;
    private Integer userId;
    private Long ttl;

    public TwitchTeamMembership() {
        ttl = Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond();
    }

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

    @DynamoDbAttribute("ttl")
    public Long getTtl() {
        return this.ttl;
    }

    public void setTtl(final Long ttl) {
        this.ttl = ttl;
    }
}
