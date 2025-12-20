package org.seattleoba.ticketing.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.ticketing.model.BevyTicket;

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
    @SqlQuery("SELECT * FROM bevy_tickets WHERE number=:ticket_number")
    @RegisterRowMapper(BevyTicketMapper.class)
    List<BevyTicket> findByTicketNumber(@Bind("ticket_number") int ticketNumber);

    /**
     * Inserts an entry into the Bevy tickets table.
     *
     * @param eventId Bevy event ID
     * @param ticketNumber Bevy ticket number
     * @param purchaserName Ticket purchaser
     * @param ticketType Ticket type
     * @param purchaseDate Ticket purchase date.
     * @param checkInDate Check in date.
     * @param accessCode Access code used to purchase ticket, if one exists.
     * @param price Ticket price, if this was a paid ticket.
     */
    @SqlUpdate("INSERT INTO bevy_tickets (event_id, number, purchaser_name, title, paid_on, checked_in_at, access_code, price) VALUES (:event_id, :ticket_number, :purchaser_name, :ticket_type, :purchase_date, :check_in_date, :access_code, :price)")
    void insert(
            @Bind("event_id") int eventId,
            @Bind("ticket_number") int ticketNumber,
            @Bind("purchaser_name") String purchaserName,
            @Bind("ticket_type") String ticketType,
            @Bind("purchase_date") Long purchaseDate,
            @Bind("check_in_date") Long checkInDate,
            @Bind("access_code") String accessCode,
            @Bind("price") BigDecimal price);
}
