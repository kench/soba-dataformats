package org.seattleoba.data.dynamodb.bean;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

/** DynamoDB item for the TwitchTeams table, keyed by Twitch team ID. */
@DynamoDbBean
public class TwitchTeam {
  private String displayName;
  private Integer id;
  private String name;
  private Long ttl;

  /** Creates an empty team whose TTL expires one day from now. */
  public TwitchTeam() {
    ttl = Instant.now().plus(1, ChronoUnit.DAYS).getEpochSecond();
  }

  @DynamoDbPartitionKey
  @DynamoDbAttribute("id")
  public Integer getId() {
    return id;
  }

  public void setId(final Integer id) {
    this.id = id;
  }

  @DynamoDbSecondaryPartitionKey(indexNames = "TeamName")
  @DynamoDbAttribute("name")
  public String getName() {
    return name;
  }

  public void setName(final String name) {
    this.name = name;
  }

  @DynamoDbAttribute("display_name")
  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(final String displayName) {
    this.displayName = displayName;
  }

  @DynamoDbAttribute("ttl")
  public Long getTtl() {
    return this.ttl;
  }

  public void setTtl(final Long ttl) {
    this.ttl = ttl;
  }
}
