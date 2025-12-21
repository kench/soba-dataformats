package org.seattleoba.ticketing.jdbi;

import org.jdbi.v3.sqlobject.config.RegisterRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.seattleoba.ticketing.model.TwitchAccount;

import java.util.List;

/**
 * DAO for Twitch account table in SQLite database.
 */
public interface TwitchAccountDao {
    /**
     * Finds all Twitch accounts matching the Twitch user ID.
     *
     * @param userId Twitch user ID
     * @return Matching Twitch accounts
     */
    @SqlQuery("SELECT * FROM twitch_accounts WHERE id=:id")
    @RegisterRowMapper(TwitchAccountMapper.class)
    List<TwitchAccount> findByUserId(@Bind("id") int userId);

    /**
     * Finds all Twitch accounts matching the Twitch user name.
     *
     * @param userName Twitch username
     * @return Matching Twitch accounts
     */
    @SqlQuery("SELECT * FROM twitch_accounts WHERE user_name=:user_name")
    @RegisterRowMapper(TwitchAccountMapper.class)
    List<TwitchAccount> findByUserName(@Bind("user_name") String userName);

    /**
     * Adds the Twitch account to the table.
     *
     * @param userId Twitch user ID
     * @param userName Twitch username
     * @param displayName User display name
     * @param userType User type
     * @param broadcasterType Broadcaster type
     * @param description User-provided description
     * @param createdAt Account creation date.
     */
    @SqlUpdate("INSERT INTO twitch_accounts (id, user_name, display_name, user_type, broadcaster_type, description, created_at) VALUES (:id, :user_name, :display_name, :user_type, :broadcaster_type, :description, :created_at)")
    void insert(
            @Bind("id") int userId,
            @Bind("user_name") String userName,
            @Bind("display_name") String displayName,
            @Bind("user_type") String userType,
            @Bind("broadcaster_type") String broadcasterType,
            @Bind("description") String description,
            @Bind("created_at") Long createdAt);
}
