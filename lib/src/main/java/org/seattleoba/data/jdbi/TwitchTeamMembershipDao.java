package org.seattleoba.data.jdbi;

import java.util.List;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.data.model.TwitchTeamMembership;

/** DAO for the twitch_team_memberships table in the SQLite database. */
public interface TwitchTeamMembershipDao {
  /**
   * Finds the members of a team.
   *
   * @param teamId Twitch team ID
   * @return the team's memberships
   */
  @SqlQuery("SELECT * FROM twitch_team_memberships WHERE team_id=:team_id")
  @RegisterRowMapper(TwitchTeamMembershipMapper.class)
  List<TwitchTeamMembership> findByTeamId(@Bind("team_id") int teamId);

  /**
   * Finds the teams a user belongs to.
   *
   * @param userId Twitch user ID
   * @return the user's memberships
   */
  @SqlQuery("SELECT * FROM twitch_team_memberships WHERE user_id=:user_id")
  @RegisterRowMapper(TwitchTeamMembershipMapper.class)
  List<TwitchTeamMembership> findByUserId(@Bind("user_id") int userId);

  /**
   * Adds a user to a team.
   *
   * @param teamId Twitch team ID
   * @param userId Twitch user ID
   */
  @SqlUpdate("INSERT INTO twitch_team_memberships (team_id, user_id) VALUES (:team_id, :user_id)")
  void insert(@Bind("team_id") int teamId, @Bind("user_id") int userId);
}
