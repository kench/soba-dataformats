package org.seattleoba.data.jdbi;

import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.statement.StatementContext;
import org.seattleoba.data.model.TwitchTeamMembership;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TwitchTeamMembershipMapper implements RowMapper<TwitchTeamMembership> {
    @Override
    public TwitchTeamMembership map(final ResultSet resultSet, final StatementContext statementContext) throws SQLException {
        final Integer userId = resultSet.getInt("user_id");
        final Integer teamId = resultSet.getInt("team_id");
        return new TwitchTeamMembership(userId, teamId);
    }
}
