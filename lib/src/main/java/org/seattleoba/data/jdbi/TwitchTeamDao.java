package org.seattleoba.data.jdbi;

import java.util.Optional;
import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.data.model.TwitchTeam;

/** DAO for the twitch_teams table in the SQLite database. */
public interface TwitchTeamDao {
  /**
   * Finds a team by ID.
   *
   * @param id Twitch team ID
   * @return the team, if present
   */
  @SqlQuery("SELECT * FROM twitch_teams WHERE id=:id")
  @RegisterRowMapper(TwitchTeamMapper.class)
  Optional<TwitchTeam> findByTeamId(@Bind("id") int id);

  /**
   * Finds a team by name.
   *
   * @param name Twitch team name
   * @return the team, if present
   */
  @SqlQuery("SELECT * FROM twitch_teams WHERE name=:name")
  @RegisterRowMapper(TwitchTeamMapper.class)
  Optional<TwitchTeam> findByTeamName(@Bind("name") String name);

  /**
   * Adds a team.
   *
   * @param id Twitch team ID
   * @param name Twitch team name
   * @param displayName Twitch team display name
   */
  @SqlUpdate("INSERT INTO twitch_teams (id, name, display_name) VALUES (:id, :name, :display_name)")
  void insert(
      @Bind("id") int id, @Bind("name") String name, @Bind("display_name") String displayName);
}
