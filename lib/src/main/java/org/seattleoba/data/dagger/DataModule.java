package org.seattleoba.data.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import org.seattleoba.data.dynamodb.bean.*;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.DynamoDbStripePaymentIntentStore;
import org.seattleoba.data.persistence.stripe.StripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.StripePaymentIntentStore;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;

import javax.inject.Named;
import javax.inject.Singleton;

@Module
public class DataModule {
    private static final String BEVY_TICKET_TABLE_NAME = "BevyTickets";
    private static final String EVENT_REGISTRATION_TABLE_NAME = "TwitchAccountsBevyTickets";
    private static final String STRIPE_BALANCE_TRANSACTIONS_TABLE_NAME = "StripeBalanceTransactions";
    private static final String STRIPE_PAYMENT_INTENTS_TABLE_NAME = "StripePaymentIntents";
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

    @Provides
    @Singleton
    @Named("DynamoDb")
    public ObjectMapper providesObjectMapperForDynamoDB() {
        return new ObjectMapper();
    }

    @Provides
    @Singleton
    public StripeBalanceTransactionStore providesStripeBalanceTransactionStore(
            final DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Named("DynamoDB") final ObjectMapper objectMapper) {
        final TableSchema<EnhancedDocument> enhancedDocumentTableSchema = TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
                .addIndexPartitionKey("Source","source", AttributeValueType.S)
                .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
                .build();
        final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
                dynamoDbEnhancedClient.table(STRIPE_BALANCE_TRANSACTIONS_TABLE_NAME, enhancedDocumentTableSchema);
        return new DynamoDbStripeBalanceTransactionStore(enhancedDocumentTable, objectMapper);
    }

    @Provides
    @Singleton
    public StripePaymentIntentStore providesStripePaymentIntentStore(
            final DynamoDbEnhancedClient dynamoDbEnhancedClient,
            @Named("DynamoDB") final ObjectMapper objectMapper) {
        final TableSchema<EnhancedDocument> enhancedDocumentTableSchema = TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
                .addIndexPartitionKey("Charge","latest_charge", AttributeValueType.S)
                .addIndexPartitionKey("Customer","customer", AttributeValueType.S)
                .addIndexPartitionKey("Description","description", AttributeValueType.S)
                .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
                .build();
        final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
                dynamoDbEnhancedClient.table(STRIPE_PAYMENT_INTENTS_TABLE_NAME, enhancedDocumentTableSchema);
        return new DynamoDbStripePaymentIntentStore(enhancedDocumentTable, objectMapper);
    }
}
