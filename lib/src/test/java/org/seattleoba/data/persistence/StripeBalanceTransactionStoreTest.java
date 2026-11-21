package org.seattleoba.data.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.dynamodb.bean.stripe.BalanceTransaction;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.StripeBalanceTransactionStore;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverterProvider;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableMetadata;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.dynamodb.services.local.embedded.DynamoDBEmbedded;

class StripeBalanceTransactionStoreTest {
  private static final String BALANCE_TRANSACTION_JSON_FILE_NAME =
      "/stripe/balance-transaction.json";

  private static final Integer AMOUNT = 999;
  private static final String CURRENCY = "usd";
  private static final Integer FEE = 5;
  private static final Integer NET = 994;
  private static final String STATUS = "available";
  private static final String TYPE = "transfer";

  private StripeBalanceTransactionStore stripeBalanceTransactionStore;
  private org.seattleoba.data.model.stripe.BalanceTransaction balanceTransaction;

  @BeforeEach
  void setup() throws IOException {
    final ObjectMapper objectMapper = new ObjectMapper();
    final DynamoDbEnhancedClient dynamoDbEnhancedClient =
        DynamoDbEnhancedClient.builder()
            .dynamoDbClient(DynamoDBEmbedded.create().dynamoDbClient())
            .build();
    final String tableName = "StripeBalanceTransactions";
    final DynamoDbTable<BalanceTransaction> balanceTransactionTable =
        dynamoDbEnhancedClient.table(tableName, TableSchema.fromBean(BalanceTransaction.class));
    balanceTransactionTable.createTable();
    final TableSchema<EnhancedDocument> enhancedDocumentTableSchema =
        TableSchema.documentSchemaBuilder()
            .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
            .addIndexPartitionKey("Source", "source", AttributeValueType.S)
            .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
            .build();
    final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
        dynamoDbEnhancedClient.table(tableName, enhancedDocumentTableSchema);
    stripeBalanceTransactionStore =
        new DynamoDbStripeBalanceTransactionStore(enhancedDocumentTable, objectMapper);
    balanceTransaction =
        objectMapper.readValue(
            StripeBalanceTransactionStoreTest.class.getResourceAsStream(
                BALANCE_TRANSACTION_JSON_FILE_NAME),
            org.seattleoba.data.model.stripe.BalanceTransaction.class);
  }

  @Test
  void retrievesBalanceTransactionById() {
    stripeBalanceTransactionStore.updateBalanceTransaction(balanceTransaction);

    final org.seattleoba.data.model.stripe.BalanceTransaction output =
        stripeBalanceTransactionStore.getBalanceTransactionById(balanceTransaction.id());

    assertEquals(balanceTransaction, output);
  }

  @Test
  void retrievesBalanceTransactionBySourceId() {
    stripeBalanceTransactionStore.updateBalanceTransaction(balanceTransaction);

    final org.seattleoba.data.model.stripe.BalanceTransaction output =
        stripeBalanceTransactionStore.getBalanceTransactionBySourceId(balanceTransaction.source());

    assertEquals(balanceTransaction, output);
  }
}
