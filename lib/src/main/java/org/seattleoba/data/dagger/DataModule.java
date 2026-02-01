package org.seattleoba.data.dagger;

import dagger.Module;
import dagger.Provides;
import org.seattleoba.data.dynamodb.bean.*;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.inject.Singleton;

@Module
public class DataModule {
    private static final String BEVY_TICKET_TABLE_NAME = "BevyTickets";
    private static final String EVENT_REGISTRATION_TABLE_NAME = "TwitchAccountsBevyTickets";
    private static final String TWITCH_ACCOUNT_TABLE_NAME = "TwitchAccounts";
    private static final String TWITCH_TEAM_TABLE_NAME = "TwitchTeams";
    private static final String TWITCH_TEAM_MEMBERSHIP_TABLE_NAME = "TwitchTeamMemberships";

    @Provides
    @Singleton
    public DynamoDbTable<BevyTicket> providesBevyTicketTable(final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(BEVY_TICKET_TABLE_NAME, TableSchema.fromBean(BevyTicket.class));
    }

    @Provides
    @Singleton
    public DynamoDbTable<EventRegistration> providesEventRegistrationTable(final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(EVENT_REGISTRATION_TABLE_NAME, TableSchema.fromBean(EventRegistration.class));
    }

    @Provides
    @Singleton
    public DynamoDbTable<TwitchAccount> providesTwitchAccountTable(final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(TWITCH_ACCOUNT_TABLE_NAME, TableSchema.fromBean(TwitchAccount.class));
    }

    @Provides
    @Singleton
    public DynamoDbTable<TwitchTeam> providesTwitchTeamTable(final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(TWITCH_TEAM_TABLE_NAME, TableSchema.fromBean(TwitchTeam.class));
    }

    @Provides
    @Singleton
    public DynamoDbTable<TwitchTeamMembership> providesTwitchTeamMembershipTable(final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
        return dynamoDbEnhancedClient.table(TWITCH_TEAM_MEMBERSHIP_TABLE_NAME, TableSchema.fromBean(TwitchTeamMembership.class));
    }
}
