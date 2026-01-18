package org.seattleoba.data.persistence;

import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.util.BevyDateUtil;
import org.seattleoba.data.util.BevyTicketNumberUtil;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Objects;

public class DynamoDbBevyTicketStore implements BevyTicketStore {
    private final DynamoDbTable<BevyTicket> dynamoDbTable;

    @Inject
    public DynamoDbBevyTicketStore(final DynamoDbTable<BevyTicket> dynamoDbTable) {
        this.dynamoDbTable = dynamoDbTable;
    }

    @Override
    public org.seattleoba.data.model.BevyTicket getTicketByNumber(final Integer eventId, final Integer ticketNumber) {
        final BevyTicket bevyTicket = dynamoDbTable.getItem(GetItemEnhancedRequest.builder()
                .key(Key.builder()
                        .partitionValue(eventId)
                        .sortValue(ticketNumber)
                        .build())
                .build());
        final String purchaseDate;
        if (Objects.nonNull(bevyTicket.getPurchaseDate())) {
            purchaseDate = BevyDateUtil.toBevyDate(bevyTicket.getPurchaseDate());
        } else {
            purchaseDate = null;
        }

        final String checkInDate;
        if (Objects.nonNull(bevyTicket.getCheckInDate())) {
            checkInDate = BevyDateUtil.toBevyDate(bevyTicket.getCheckInDate());
        } else {
            checkInDate = null;
        }

        final String price;
        if (Objects.nonNull(bevyTicket.getPrice())) {
            price = bevyTicket.getPrice().toString();
        } else {
            price = null;
        }

        return new org.seattleoba.data.model.BevyTicket(
                BevyTicketNumberUtil.toString(bevyTicket.getId()),
                bevyTicket.getOrderId(),
                bevyTicket.getPurchaserName(),
                bevyTicket.getTicketType(),
                purchaseDate,
                checkInDate,
                bevyTicket.getAccessCode(),
                price);
    }

    @Override
    public org.seattleoba.data.model.BevyTicket getTicketByTwitchId(final Integer eventId, final Integer twitchId) {
        return null;
    }

    @Override
    public org.seattleoba.data.model.BevyTicket getTicketsForTwitchId(final Integer twitchId) {
        return null;
    }

    @Override
    public void insertBevyTicket(final Integer eventId, final org.seattleoba.data.model.BevyTicket bevyTicket) {
        final BevyTicket ticket = new BevyTicket();
        ticket.setEventId(eventId);
        ticket.setId(BevyTicketNumberUtil.toInteger(bevyTicket.ticketNumber()));
        ticket.setOrderId(bevyTicket.orderNumber());
        ticket.setPurchaserName(bevyTicket.purchaserName());
        ticket.setTicketType(bevyTicket.ticketType());
        ticket.setPurchaseDate(BevyDateUtil.toUnixEpochInSeconds(bevyTicket.purchaseDate()));
        ticket.setAccessCode(bevyTicket.accessCode());
        ticket.setPrice(new BigDecimal(bevyTicket.price()));

        if (Objects.nonNull(bevyTicket.checkInDate())) {
            ticket.setCheckInDate(BevyDateUtil.toUnixEpochInSeconds(bevyTicket.checkInDate()));
        }

        dynamoDbTable.putItem(ticket);
    }
}
