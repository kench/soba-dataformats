package org.seattleoba.data.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.data.model.BevyTicket;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO for JDBI access to the SQL database.
 */
public interface BevyTicketDao {
    /**
     * Finds all Bevy tickets matching the ticket number.
     *
     * @param ticketNumber Truncated Bevy ticket number
     * @return Matching Bevy tickets.
     */
    @SqlQuery("SELECT * FROM bevy_tickets WHERE id=:id")
    @RegisterRowMapper(BevyTicketMapper.class)
    List<BevyTicket> findByTicketNumber(@Bind("id") int ticketNumber);

    /**
     * Finds all Bevy tickets for the given Twitch ID.
     *
     * @param twitchId Twitch ID
     * @return Matching Bevy tickets.
     */
    @SqlQuery("SELECT * FROM bevy_tickets JOIN twitch_accounts_bevy_tickets ON bevy_tickets.id = twitch_accounts_bevy_tickets.bevy_ticket_id WHERE twitch_id=:twitch_id AND bevy_event_id=:event_id;")
    @RegisterRowMapper(BevyTicketMapper.class)
    List<BevyTicket> findByEventAndTwitchId(@Bind("event_id") int eventId, @Bind("twitch_id") int twitchId);

    /**
     * Finds all Bevy tickets for the given Twitch ID.
     *
     * @param twitchId Twitch ID
     * @return Matching Bevy tickets.
     */
    @SqlQuery("SELECT * FROM bevy_tickets JOIN twitch_accounts_bevy_tickets ON bevy_tickets.id = twitch_accounts_bevy_tickets.bevy_ticket_id WHERE twitch_id=:id;")
    @RegisterRowMapper(BevyTicketMapper.class)
    List<BevyTicket> getTicketsByTwitchId(@Bind("id") int twitchId);

    /**
     * Inserts an entry into the Bevy tickets table.
     *
     * @param eventId Bevy event ID
     * @param orderNumber Bevy order number
     * @param ticketNumber Bevy ticket number
     * @param ticketId Bevy ticket ID
     * @param purchaserName Ticket purchaser
     * @param ticketType Ticket type
     * @param purchaseDate Ticket purchase date.
     * @param checkInDate Check in date.
     * @param accessCode Access code used to purchase ticket, if one exists.
     * @param price Ticket price, if this was a paid ticket.
     */
    @SqlUpdate("INSERT INTO bevy_tickets (event_id, order_id, id, ticket_id, purchaser_name, title, paid_on, checked_in_at, access_code, price) VALUES (:event_id, :order_id, :id, :ticket_id, :purchaser_name, :ticket_type, :purchase_date, :check_in_date, :access_code, :price)")
    void insert(
            @Bind("event_id") int eventId,
            @Bind("order_id") String orderNumber,
            @Bind("id") int ticketNumber,
            @Bind("ticket_id") String ticketId,
            @Bind("purchaser_name") String purchaserName,
            @Bind("ticket_type") String ticketType,
            @Bind("purchase_date") Long purchaseDate,
            @Bind("check_in_date") Long checkInDate,
            @Bind("access_code") String accessCode,
            @Bind("price") BigDecimal price);
}
