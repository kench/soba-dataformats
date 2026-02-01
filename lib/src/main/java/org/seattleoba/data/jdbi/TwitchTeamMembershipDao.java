package org.seattleoba.data.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.data.model.TwitchTeamMembership;

import java.util.List;

public interface TwitchTeamMembershipDao {
    @SqlQuery("SELECT * FROM twitch_team_memberships WHERE team_id=:team_id")
    @RegisterRowMapper(TwitchTeamMembershipMapper.class)
    List<TwitchTeamMembership> findByTeamId(@Bind("team_id") int teamId);

    @SqlQuery("SELECT * FROM twitch_team_memberships WHERE user_id=:user_id")
    @RegisterRowMapper(TwitchTeamMembershipMapper.class)
    List<TwitchTeamMembership> findByUserId(@Bind("user_id") int userId);

    @SqlUpdate("INSERT INTO twitch_team_memberships (team_id, user_id) VALUES (:team_id, :user_id)")
    void insert(@Bind("team_id") int teamId, @Bind("user_id") int userId);
}
