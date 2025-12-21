package org.seattleoba.ticketing.jdbi;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.seattleoba.ticketing.model.TwitchAccount;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * RowMapper implementation for TwitchAccount.
 */
public class TwitchAccountMapper implements RowMapper<TwitchAccount> {
    @Override
    public TwitchAccount map(
            final ResultSet resultSet,
            final StatementContext statementContext) throws SQLException {
        final Integer id = resultSet.getInt("id");
        final String username = resultSet.getString("user_name");
        final String displayName = resultSet.getString("display_name");
        final String userType = resultSet.getString("user_type");
        final String broadcasterType = resultSet.getString("broadcaster_type");
        final String description = resultSet.getString("description");
        final Long createdAt = resultSet.getLong("created_at");
        return new TwitchAccount(
                id,
                username,
                displayName,
                userType,
                broadcasterType,
                description,
                createdAt);
    }
}
