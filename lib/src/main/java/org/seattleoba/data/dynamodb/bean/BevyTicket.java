package org.seattleoba.data.dynamodb.bean;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.math.BigDecimal;

@DynamoDbBean
public class BevyTicket {
    private Integer id;
    private Integer eventId;
    private String orderId;
    private String purchaserName;
    private String ticketType;
    private Long purchaseDate;
    private Long checkInDate;
    private String accessCode;
    private BigDecimal price;

    @DynamoDbSortKey
    @DynamoDbAttribute("id")
    public Integer getId() {
        return id;
    }

    public void setId(final Integer id) {
        this.id = id;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute(value = "event_id")
    public Integer getEventId() {
        return eventId;
    }

    public void setEventId(final Integer eventId) {
        this.eventId = eventId;
    }

    @DynamoDbAttribute(value = "order_id")
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(final String orderId) {
        this.orderId = orderId;
    }

    @DynamoDbAttribute(value = "purchaser_name")
    public String getPurchaserName() {
        return purchaserName;
    }

    public void setPurchaserName(final String purchaserName) {
        this.purchaserName = purchaserName;
    }

    @DynamoDbAttribute(value = "ticket_type")
    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(final String ticketType) {
        this.ticketType = ticketType;
    }

    @DynamoDbAttribute(value = "purchase_date")
    public Long getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(final Long purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    @DynamoDbAttribute(value = "check_in_date")
    public Long getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(final Long checkInDate) {
        this.checkInDate = checkInDate;
    }

    @DynamoDbAttribute(value = "access_code")
    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(final String accessCode) {
        this.accessCode = accessCode;
    }

    @DynamoDbAttribute(value = "price")
    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(final BigDecimal price) {
        this.price = price;
    }
}
