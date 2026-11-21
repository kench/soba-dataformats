package org.seattleoba.data.dynamodb.bean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondarySortKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/** DynamoDB item for the TwitchTeamMemberships table, keyed by team ID and user ID. */
@DynamoDbBean
public class TwitchTeamMembership {
  private Integer teamId;
  private Integer userId;
  private Long ttl;

  /** Creates an empty membership whose TTL expires one day from now. */
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
