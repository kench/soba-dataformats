package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.*;

@DynamoDbBean
public class EventRegistration {
    private Integer id;
    private Integer eventId;
    private Integer twitchId;

    @DynamoDbPartitionKey
    @DynamoDbSecondarySortKey(indexNames = "EventId")
    @DynamoDbAttribute("bevy_ticket_id")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @DynamoDbSecondaryPartitionKey(indexNames = "EventId")
    @DynamoDbSecondarySortKey(indexNames = "UserId")
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
