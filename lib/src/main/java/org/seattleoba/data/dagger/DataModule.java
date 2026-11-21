package org.seattleoba.data.dagger;

import com.fasterxml.jackson.databind.ObjectMapper;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import org.seattleoba.data.dynamodb.bean.BevyTicket;
import org.seattleoba.data.dynamodb.bean.EventRegistration;
import org.seattleoba.data.dynamodb.bean.TwitchAccount;
import org.seattleoba.data.dynamodb.bean.TwitchTeam;
import org.seattleoba.data.dynamodb.bean.TwitchTeamMembership;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeChargeStore;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeCustomerStore;
import org.seattleoba.data.persistence.stripe.DynamoDbStripePaymentIntentStore;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeRefundStore;
import org.seattleoba.data.persistence.stripe.StripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.StripeChargeStore;
import org.seattleoba.data.persistence.stripe.StripeCustomerStore;
import org.seattleoba.data.persistence.stripe.StripePaymentIntentStore;
import org.seattleoba.data.persistence.stripe.StripeRefundStore;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;

/** Dagger module that provides the SOBA DynamoDB tables and Stripe stores. */
@Module
public class DataModule {
  private static final String BEVY_TICKET_TABLE_NAME = "BevyTickets";
  private static final String EVENT_REGISTRATION_TABLE_NAME = "TwitchAccountsBevyTickets";
  private static final String STRIPE_BALANCE_TRANSACTIONS_TABLE_NAME = "StripeBalanceTransactions";
  private static final String STRIPE_CHARGES_TABLE_NAME = "StripeCharges";
  private static final String STRIPE_CUSTOMERS_TABLE_NAME = "StripeCustomers";
  private static final String STRIPE_PAYMENT_INTENTS_TABLE_NAME = "StripePaymentIntents";
  private static final String STRIPE_REFUNDS_TABLE_NAME = "StripeRefunds";
  private static final String TWITCH_ACCOUNT_TABLE_NAME = "TwitchAccounts";
  private static final String TWITCH_TEAM_TABLE_NAME = "TwitchTeams";
  private static final String TWITCH_TEAM_MEMBERSHIP_TABLE_NAME = "TwitchTeamMemberships";

  /**
   * Provides the BevyTickets table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @return the table
   */
  @Provides
  @Singleton
  public DynamoDbTable<BevyTicket> providesBevyTicketTable(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    return dynamoDbEnhancedClient.table(
        BEVY_TICKET_TABLE_NAME, TableSchema.fromBean(BevyTicket.class));
  }

  /**
   * Provides the TwitchAccountsBevyTickets table, which links tickets to Twitch accounts.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @return the table
   */
  @Provides
  @Singleton
  public DynamoDbTable<EventRegistration> providesEventRegistrationTable(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    return dynamoDbEnhancedClient.table(
        EVENT_REGISTRATION_TABLE_NAME, TableSchema.fromBean(EventRegistration.class));
  }

  /**
   * Provides the TwitchAccounts table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @return the table
   */
  @Provides
  @Singleton
  public DynamoDbTable<TwitchAccount> providesTwitchAccountTable(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    return dynamoDbEnhancedClient.table(
        TWITCH_ACCOUNT_TABLE_NAME, TableSchema.fromBean(TwitchAccount.class));
  }

  /**
   * Provides the TwitchTeams table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @return the table
   */
  @Provides
  @Singleton
  public DynamoDbTable<TwitchTeam> providesTwitchTeamTable(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    return dynamoDbEnhancedClient.table(
        TWITCH_TEAM_TABLE_NAME, TableSchema.fromBean(TwitchTeam.class));
  }

  /**
   * Provides the TwitchTeamMemberships table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @return the table
   */
  @Provides
  @Singleton
  public DynamoDbTable<TwitchTeamMembership> providesTwitchTeamMembershipTable(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient) {
    return dynamoDbEnhancedClient.table(
        TWITCH_TEAM_MEMBERSHIP_TABLE_NAME, TableSchema.fromBean(TwitchTeamMembership.class));
  }

  /**
   * Provides the store for the StripeBalanceTransactions table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @param objectMapper Jackson mapper for Stripe JSON
   * @return the store
   */
  @Provides
  @Singleton
  public StripeBalanceTransactionStore providesStripeBalanceTransactionStore(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient, final ObjectMapper objectMapper) {
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Source", "source", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(
            STRIPE_BALANCE_TRANSACTIONS_TABLE_NAME, enhancedDocumentTableSchema);
    return new DynamoDbStripeBalanceTransactionStore(enhancedDocumentTable, objectMapper);
  }

  /**
   * Provides the store for the StripeCharges table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @param objectMapper Jackson mapper for Stripe JSON
   * @return the store
   */
  @Provides
  @Singleton
  public StripeChargeStore providesStripeChargeStore(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient, final ObjectMapper objectMapper) {
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Customer", "customer", AttributeValueType.S)
            .addIndexPartitionKey("PaymentIntent", "payment_intent", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(STRIPE_CHARGES_TABLE_NAME, enhancedDocumentTableSchema);
    return new DynamoDbStripeChargeStore(enhancedDocumentTable, objectMapper);
  }

  /**
   * Provides the store for the StripeCustomers table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @param objectMapper Jackson mapper for Stripe JSON
   * @return the store
   */
  @Provides
  @Singleton
  public StripeCustomerStore providesStripeCustomerStore(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient, final ObjectMapper objectMapper) {
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Email", "email", AttributeValueType.S)
            .addIndexPartitionKey("Name", "name", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(STRIPE_CUSTOMERS_TABLE_NAME, enhancedDocumentTableSchema);
    return new DynamoDbStripeCustomerStore(enhancedDocumentTable, objectMapper);
  }

  /**
   * Provides the store for the StripePaymentIntents table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @param objectMapper Jackson mapper for Stripe JSON
   * @return the store
   */
  @Provides
  @Singleton
  public StripePaymentIntentStore providesStripePaymentIntentStore(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient, final ObjectMapper objectMapper) {
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Charge", "latest_charge", AttributeValueType.S)
            .addIndexPartitionKey("Customer", "customer", AttributeValueType.S)
            .addIndexPartitionKey("Description", "description", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(
            STRIPE_PAYMENT_INTENTS_TABLE_NAME, enhancedDocumentTableSchema);
    return new DynamoDbStripePaymentIntentStore(enhancedDocumentTable, objectMapper);
  }

  /**
   * Provides the store for the StripeRefunds table.
   *
   * @param dynamoDbEnhancedClient enhanced DynamoDB client
   * @param objectMapper Jackson mapper for Stripe JSON
   * @return the store
   */
  @Provides
  @Singleton
  public StripeRefundStore providesStripeRefundsStore(
      final DynamoDbEnhancedClient dynamoDbEnhancedClient, final ObjectMapper objectMapper) {
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Charge", "charge", AttributeValueType.S)
            .addIndexPartitionKey("PaymentIntent", "payment_intent", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(STRIPE_REFUNDS_TABLE_NAME, enhancedDocumentTableSchema);
    return new DynamoDbStripeRefundStore(enhancedDocumentTable, objectMapper);
  }
}
