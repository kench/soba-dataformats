package org.seattleoba.data.dagger;

import dagger.Module;
import dagger.Provides;
import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.dynamodb.bean.EventRegistration;
import org.seattleoba.data.dynamodb.bean.TwitchAccount;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

import javax.inject.Singleton;

@Module
public class DataModule {
    private static final String BEVY_TICKET_TABLE_NAME = "BevyTickets";
    private static final String EVENT_REGISTRATION_TABLE_NAME = "EventRegistrations";
    private static final String TWITCH_ACCOUNT_TABLE_NAME = "TwitchAccounts";

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
}
