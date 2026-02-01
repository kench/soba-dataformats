package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@DynamoDbBean
public class TwitchTeam {
    private String displayName;
    private Integer id;
    private String name;
    private Long ttl;

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
    public String getName() { return name; }

    public void setName(final String name) {
        this.name = name;
    }

    @DynamoDbAttribute("display_name")
    public String getDisplayName() { return displayName; }

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
