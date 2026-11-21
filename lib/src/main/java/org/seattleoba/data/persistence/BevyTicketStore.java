package org.seattleoba.data.persistence;

import java.util.List;
import org.seattleoba.data.model.BevyTicket;

/** Persistence layer for Bevy tickets. */
public interface BevyTicketStore {
  /**
   * Returns the ticket with the given number for an event.
   *
   * @param eventId Bevy event ID
   * @param ticketNumber numeric ticket number (see {@code BevyTicketNumberUtil})
   * @return the matching ticket
   */
  BevyTicket getTicketByNumber(final Integer eventId, final Integer ticketNumber);

  /**
   * Returns the ticket registered to a Twitch account for an event.
   *
   * @param eventId Bevy event ID
   * @param twitchId Twitch user ID
   * @return the matching ticket
   */
  BevyTicket getTicketByTwitchId(final Integer eventId, final Integer twitchId);

  /**
   * Returns every ticket registered to a Twitch account, across all events.
   *
   * @param twitchId Twitch user ID
   * @return the matching tickets, possibly empty
   */
  List<BevyTicket> getTicketsForTwitchId(final Integer twitchId);

  /**
   * Stores a ticket for an event.
   *
   * @param eventId Bevy event ID
   * @param bevyTicket ticket from the Bevy CSV export
   */
  void insertBevyTicket(final Integer eventId, final BevyTicket bevyTicket);
}
