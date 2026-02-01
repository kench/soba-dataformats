package org.seattleoba.data.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.data.model.TwitchTeam;

import java.util.Optional;

public interface TwitchTeamDao {
    @SqlQuery("SELECT * FROM twitch_teams WHERE id=:id")
    @RegisterRowMapper(TwitchTeamMapper.class)
    Optional<TwitchTeam> findByTeamId(@Bind("id") int id);

    @SqlQuery("SELECT * FROM twitch_teams WHERE name=:name")
    @RegisterRowMapper(TwitchTeamMapper.class)
    Optional<TwitchTeam> findByTeamName(@Bind("name") String name);

    @SqlUpdate("INSERT INTO twitch_teams (id, name, display_name) VALUES (:id, :name, :display_name)")
    void insert(@Bind("id") int id, @Bind("name") String name, @Bind("display_name") String displayName);
}
