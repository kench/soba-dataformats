package org.seattleoba.data.jdbi;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.seattleoba.data.model.TwitchTeam;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TwitchTeamMapper implements RowMapper<TwitchTeam> {
    @Override
    public TwitchTeam map(final ResultSet resultSet, final StatementContext statementContext) throws SQLException {
        final Integer id = resultSet.getInt("id");
        final String name = resultSet.getString("name");
        final String displayName = resultSet.getString("display_name");
        return new TwitchTeam(id, name, displayName);
    }
}
