package org.seattleoba.data.persistence;

import org.seattleoba.data.model.BevyTicket;

/**
 * Persistence layer for Bevy tickets.
 */
public interface BevyTicketStore {
    BevyTicket getTicketByNumber(final Integer eventId, final Integer ticketNumber);
    BevyTicket getTicketByTwitchId(final Integer eventId, final Integer twitchId);
    BevyTicket getTicketsForTwitchId(final Integer twitchId);
    void insertBevyTicket(final Integer eventId, final BevyTicket bevyTicket);
}
