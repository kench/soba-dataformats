package org.seattleoba.data.jdbi;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.seattleoba.data.model.BevyTicket;
import org.seattleoba.data.util.BevyDateUtil;
import org.seattleoba.data.util.BevyTicketNumberUtil;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation for BevyTicket.
 */
public class BevyTicketMapper implements RowMapper<BevyTicket> {
    @Override
    public BevyTicket map(
            final ResultSet resultSet,
            final StatementContext statementContext) throws SQLException {
        final String ticketNumber = BevyTicketNumberUtil.toString(resultSet.getInt("id"));
        final String orderNumber = resultSet.getString("order_id");
        final String purchaserName = resultSet.getString("purchaser_name");
        final String ticketType = resultSet.getString("title");
        final String accessCode = resultSet.getString("access_code");
        final String price = String.valueOf(resultSet.getBigDecimal("price"));
        final String purchaseDate = toBevyTicketDate(resultSet.getLong("paid_on"));

        final String checkInDate;
        if (resultSet.getLong("checked_in_at") == 0) {
            checkInDate = null;
        } else {
            checkInDate = toBevyTicketDate(resultSet.getLong("checked_in_at"));
        }

        return new BevyTicket(
                ticketNumber,
                orderNumber,
                purchaserName,
                ticketType,
                purchaseDate,
                checkInDate,
                accessCode,
                price);
    }

    private String toBevyTicketDate(final Long date) {
        return BevyDateUtil.toBevyDate(date);
    }
}
