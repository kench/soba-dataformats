package org.seattleoba.data.persistence;

import org.seattleoba.data.jdbi.BevyTicketDao;
import org.seattleoba.data.model.BevyTicket;
import org.seattleoba.data.util.BevyDateUtil;
import org.seattleoba.data.util.BevyTicketNumberUtil;

import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class JdbiBevyTicketStore implements BevyTicketStore {
    private final BevyTicketDao bevyTicketDao;

    @Inject
    public JdbiBevyTicketStore(final BevyTicketDao bevyTicketDao) {
        this.bevyTicketDao = bevyTicketDao;
    }

    @Override
    public BevyTicket getTicketByNumber(final Integer eventId, final Integer ticketNumber) {
        return bevyTicketDao.findByTicketNumber(ticketNumber).getFirst();
    }

    @Override
    public BevyTicket getTicketByTwitchId(final Integer eventId, final Integer twitchId) {
        throw new IllegalStateException("Unsupported operation");
    }

    @Override
    public List<BevyTicket> getTicketsForTwitchId(final Integer twitchId) {
        throw new IllegalStateException("Unsupported operation");
    }

    @Override
    public void insertBevyTicket(final Integer eventId, final BevyTicket bevyTicket) {
        final Long purchaseDate;
        if (Objects.nonNull(bevyTicket.purchaseDate())) {
            purchaseDate = BevyDateUtil.toUnixEpochInSeconds(bevyTicket.purchaseDate());
        } else {
            purchaseDate = null;
        }

        final Long checkInDate;
        if (Objects.nonNull(bevyTicket.checkInDate())) {
            checkInDate = BevyDateUtil.toUnixEpochInSeconds(bevyTicket.checkInDate());
        } else {
            checkInDate = null;
        }

        final BigDecimal price;
        if (Objects.nonNull(bevyTicket.price())) {
            price = new BigDecimal(bevyTicket.price());
        } else {
            price = null;
        }

        bevyTicketDao.insert(
                eventId,
                bevyTicket.orderNumber(),
                BevyTicketNumberUtil.toInteger(bevyTicket.ticketNumber()),
                bevyTicket.purchaserName(),
                bevyTicket.ticketType(),
                purchaseDate,
                checkInDate,
                bevyTicket.accessCode(),
                price);
    }
}
