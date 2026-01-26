package org.seattleoba.data.persistence;

import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.dynamodb.bean.EventRegistration;
import org.seattleoba.data.util.BevyDateUtil;
import org.seattleoba.data.util.BevyTicketNumberUtil;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbIndex;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.model.GetItemEnhancedRequest;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryEnhancedRequest;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class DynamoDbBevyTicketStore implements BevyTicketStore {
    private final DynamoDbTable<BevyTicket> bevyTicketDynamoDbTable;
    private final DynamoDbTable<EventRegistration> eventRegistrationDynamoDbTable;

    @Inject
    public DynamoDbBevyTicketStore(
            final DynamoDbTable<BevyTicket> bevyTicketDynamoDbTable,
            final DynamoDbTable<EventRegistration> eventRegistrationDynamoDbTable) {
        this.bevyTicketDynamoDbTable = bevyTicketDynamoDbTable;
        this.eventRegistrationDynamoDbTable = eventRegistrationDynamoDbTable;
    }

    @Override
    public org.seattleoba.data.model.BevyTicket getTicketByNumber(final Integer eventId, final Integer ticketNumber) {
        final BevyTicket bevyTicket = bevyTicketDynamoDbTable.getItem(GetItemEnhancedRequest.builder()
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
                bevyTicket.getTicketId(),
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
        final DynamoDbIndex<EventRegistration> index = eventRegistrationDynamoDbTable.index("UserId");
        final EventRegistration eventRegistration = index.query(QueryEnhancedRequest.builder()
                .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                        .partitionValue(twitchId)
                        .sortValue(eventId)
                        .build()))
                .build())
                .stream()
                .flatMap(page -> page.items().stream())
                .toList()
                .getFirst();
        return getTicketByNumber(eventId, eventRegistration.getId());
    }

    @Override
    public List<org.seattleoba.data.model.BevyTicket> getTicketsForTwitchId(final Integer twitchId) {
        final DynamoDbIndex<EventRegistration> index = eventRegistrationDynamoDbTable.index("UserId");
        return index.query(QueryEnhancedRequest.builder()
                        .queryConditional(QueryConditional.keyEqualTo(Key.builder()
                                .partitionValue(twitchId)
                                .build()))
                        .build())
                .stream()
                .flatMap(page -> page.items().stream())
                .map(eventRegistration ->
                        getTicketByNumber(eventRegistration.getEventId(), eventRegistration.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public void insertBevyTicket(final Integer eventId, final org.seattleoba.data.model.BevyTicket bevyTicket) {
        final BevyTicket ticket = new BevyTicket();
        ticket.setEventId(eventId);
        ticket.setTicketId(bevyTicket.ticketNumber());
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

        bevyTicketDynamoDbTable.putItem(ticket);
    }
}
