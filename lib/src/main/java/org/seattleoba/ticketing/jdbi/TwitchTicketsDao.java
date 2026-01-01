package org.seattleoba.ticketing.jdbi;

import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;

public interface TwitchTicketsDao {
    @SqlQuery("SELECT twitch_id FROM twitch_accounts_bevy_tickets WHERE bevy_ticket_id=:ticket_id")
    Integer getUserForTicket(@Bind("ticket_id") int ticketNumber);

    @SqlQuery("SELECT twitch_id FROM twitch_accounts_bevy_tickets WHERE bevy_ticket_id=:ticket_id AND bevy_event_id=:event_id")
    Integer getUserForEventAndTicket(@Bind("event_id") int eventId, @Bind("ticket_id") int ticketNumber);

    @SqlQuery("SELECT bevy_ticket_id FROM twitch_accounts_bevy_tickets WHERE twitch_id=:user_id")
    List<Integer> getTicketsForUser(@Bind("user_id") int userId);

    @SqlQuery("SELECT bevy_ticket_id FROM twitch_accounts_bevy_tickets WHERE twitch_id=:user_id AND bevy_event_id=:event_id")
    List<Integer> getTicketsForUserAndEvent(@Bind("user_id") int userId, @Bind("event_id") int eventId);

    @SqlUpdate("INSERT INTO twitch_accounts_bevy_tickets (twitch_id, bevy_event_id, bevy_ticket_id) VALUES (:user_id, :event_id, :ticket_id)")
    void insert(@Bind("user_id") int userId, @Bind("event_id") int eventId, @Bind("ticket_id") int ticketNumber);
}
