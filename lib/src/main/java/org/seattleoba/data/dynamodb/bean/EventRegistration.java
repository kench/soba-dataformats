package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSecondaryPartitionKey;

@DynamoDbBean
public class EventRegistration {
    private Integer id;
    private Integer eventId;
    private Integer twitchId;

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "EventId")
    @DynamoDbAttribute(value = "bevy_event_id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(final Integer eventId) {
        this.eventId = eventId;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "UserId")
    @DynamoDbAttribute(value = "twitch_id")
    public Integer getTwitchId() {
        return twitchId;
    }

    public void setTwitchId(final Integer twitchId) {
        this.twitchId = twitchId;
    }
}
