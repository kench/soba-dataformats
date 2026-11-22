package org.seattleoba.data.dagger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.model.stripe.BalanceTransaction;
import org.seattleoba.data.model.stripe.Charge;
import org.seattleoba.data.model.stripe.Customer;
import org.seattleoba.data.model.stripe.PaymentIntent;
import org.seattleoba.data.model.stripe.Refund;
import org.seattleoba.data.persistence.stripe.StripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.StripeChargeStore;
import org.seattleoba.data.persistence.stripe.StripeCustomerStore;
import org.seattleoba.data.persistence.stripe.StripePaymentIntentStore;
import org.seattleoba.data.persistence.stripe.StripeRefundStore;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.dynamodb.services.local.embedded.DynamoDBEmbedded;

/** Exercises every provider in {@link DataModule} against DynamoDB Local. */
class DataModuleTest {
  private final DataModule dataModule = new DataModule();
  private final ObjectMapper objectMapper = new ObjectMapper();
  private DynamoDbEnhancedClient client;

  @BeforeEach
  void setup() {
    client =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DynamoDBEmbedded.create().dynamoDbClient())
            .build();
    createTable(
        "StripeBalanceTransactions",
        org.seattleoba.data.dynamodb.bean.stripe.BalanceTransaction.class);
    createTable("StripeCharges", org.seattleoba.data.dynamodb.bean.stripe.Charge.class);
    createTable("StripeCustomers", org.seattleoba.data.dynamodb.bean.stripe.Customer.class);
    createTable(
        "StripePaymentIntents", org.seattleoba.data.dynamodb.bean.stripe.PaymentIntent.class);
    createTable("StripeRefunds", org.seattleoba.data.dynamodb.bean.stripe.Refund.class);
  }

  @Test
  void providesTablesWithProductionNames() {
    assertEquals("BarcodeScans", dataModule.providesBarcodeScanTable(client).tableName());
    assertEquals("BevyTickets", dataModule.providesBevyTicketTable(client).tableName());
    assertEquals(
        "TwitchAccountsBevyTickets", dataModule.providesEventRegistrationTable(client).tableName());
    assertEquals("TwitchAccounts", dataModule.providesTwitchAccountTable(client).tableName());
    assertEquals("TwitchTeams", dataModule.providesTwitchTeamTable(client).tableName());
    assertEquals(
        "TwitchTeamMemberships", dataModule.providesTwitchTeamMembershipTable(client).tableName());
  }

  @Test
  void balanceTransactionStoreReadsByIdAndSource() throws IOException {
    final StripeBalanceTransactionStore store =
        dataModule.providesStripeBalanceTransactionStore(client, objectMapper);
    final BalanceTransaction balanceTransaction =
        read("balance-transaction.json", BalanceTransaction.class);

    store.updateBalanceTransaction(balanceTransaction);

    assertEquals(balanceTransaction, store.getBalanceTransactionById(balanceTransaction.id()));
    assertEquals(
        balanceTransaction, store.getBalanceTransactionBySourceId(balanceTransaction.source()));
    assertNull(store.getBalanceTransactionBySourceId("tr_missing"));
  }

  @Test
  void chargeStoreReadsByIdCustomerAndPaymentIntent() throws IOException {
    final StripeChargeStore store = dataModule.providesStripeChargeStore(client, objectMapper);
    final Charge charge = read("charge.json", Charge.class);

    store.updateCharge(charge);

    assertEquals(charge, store.getChargeById(charge.id()));
    assertEquals(List.of(charge), store.findChargesByCustomerId(charge.customer()));
    assertEquals(List.of(charge), store.findChargesByPaymentIntentId(charge.paymentIntent()));
    assertEquals(List.of(), store.findChargesByCustomerId("cus_missing"));
  }

  @Test
  void customerStoreReadsByIdEmailAndName() throws IOException {
    final StripeCustomerStore store = dataModule.providesStripeCustomerStore(client, objectMapper);
    final Customer customer = read("customer.json", Customer.class);

    store.updateCustomer(customer);

    assertEquals(customer, store.getCustomerById(customer.id()));
    assertEquals(customer, store.findCustomerByEmail(customer.email()));
    assertEquals(customer, store.findCustomerByName(customer.name()));
    assertNull(store.findCustomerByEmail("missing@example.com"));
  }

  @Test
  void paymentIntentStoreReadsByIdDescriptionAndLatestCharge() throws IOException {
    final StripePaymentIntentStore store =
        dataModule.providesStripePaymentIntentStore(client, objectMapper);
    final PaymentIntent paymentIntent = read("payment-intent.json", PaymentIntent.class);

    store.updatePaymentIntent(paymentIntent);

    assertEquals(paymentIntent, store.getPaymentIntentById(paymentIntent.id()));
    assertEquals(paymentIntent, store.findPaymentIntentByDescription(paymentIntent.description()));
    assertEquals(
        paymentIntent, store.findPaymentIntentByLatestCharge(paymentIntent.latestCharge()));
    assertNull(store.findPaymentIntentByDescription("missing"));
  }

  @Test
  void refundStoreReadsByIdChargeAndPaymentIntent() throws IOException {
    final StripeRefundStore store = dataModule.providesStripeRefundsStore(client, objectMapper);
    final Refund refund = read("refund.json", Refund.class);

    store.updateRefund(refund);

    assertEquals(refund, store.getRefundById(refund.id()));
    assertEquals(List.of(refund), store.findRefundsByChargeId(refund.charge()));
    assertEquals(List.of(refund), store.findRefundsByPaymentIntentId(refund.paymentIntent()));
  }

  private <T> void createTable(final String tableName, final Class<T> beanClass) {
    client.table(tableName, TableSchema.fromBean(beanClass)).createTable();
  }

  private <T> T read(final String fileName, final Class<T> type) throws IOException {
    return objectMapper.readValue(
        DataModuleTest.class.getResourceAsStream("/stripe/" + fileName), type);
  }
}
