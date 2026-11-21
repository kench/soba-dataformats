package org.seattleoba.data.jdbi;

import java.util.List;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

/** DAO for the twitch_accounts_bevy_tickets table, which links Bevy tickets to Twitch accounts. */
public interface TwitchTicketsDao {
  /**
   * Finds the Twitch user registered to a ticket.
   *
   * @param ticketNumber numeric Bevy ticket number
   * @return the Twitch user ID, or {@code null} if the ticket isn't registered
   */
  @SqlQuery("SELECT twitch_id FROM twitch_accounts_bevy_tickets WHERE bevy_ticket_id=:ticket_id")
  Integer getUserForTicket(@Bind("ticket_id") int ticketNumber);

  /**
   * Finds the Twitch user registered to a ticket for an event.
   *
   * @param eventId Bevy event ID
   * @param ticketNumber numeric Bevy ticket number
   * @return the Twitch user ID, or {@code null} if the ticket isn't registered
   */
  @SqlQuery(
      "SELECT twitch_id FROM twitch_accounts_bevy_tickets WHERE bevy_ticket_id=:ticket_id AND"
          + " bevy_event_id=:event_id")
  Integer getUserForEventAndTicket(
      @Bind("event_id") int eventId, @Bind("ticket_id") int ticketNumber);

  /**
   * Finds the tickets registered to a Twitch user.
   *
   * @param userId Twitch user ID
   * @return numeric Bevy ticket numbers
   */
  @SqlQuery("SELECT bevy_ticket_id FROM twitch_accounts_bevy_tickets WHERE twitch_id=:user_id")
  List<Integer> getTicketsForUser(@Bind("user_id") int userId);

  /**
   * Finds the tickets registered to a Twitch user for an event.
   *
   * @param userId Twitch user ID
   * @param eventId Bevy event ID
   * @return numeric Bevy ticket numbers
   */
  @SqlQuery(
      "SELECT bevy_ticket_id FROM twitch_accounts_bevy_tickets WHERE twitch_id=:user_id AND"
          + " bevy_event_id=:event_id")
  List<Integer> getTicketsForUserAndEvent(
      @Bind("user_id") int userId, @Bind("event_id") int eventId);

  /**
   * Registers a ticket to a Twitch user.
   *
   * @param userId Twitch user ID
   * @param eventId Bevy event ID
   * @param ticketNumber numeric Bevy ticket number
   */
  @SqlUpdate(
      "INSERT INTO twitch_accounts_bevy_tickets (twitch_id, bevy_event_id, bevy_ticket_id) VALUES"
          + " (:user_id, :event_id, :ticket_id)")
  void insert(
      @Bind("user_id") int userId,
      @Bind("event_id") int eventId,
      @Bind("ticket_id") int ticketNumber);
}
