package org.seattleoba.data.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.seattleoba.data.dynamodb.bean.stripe.BalanceTransaction;
import org.seattleoba.data.persistence.stripe.DynamoDbStripeBalanceTransactionStore;
import org.seattleoba.data.persistence.stripe.StripeBalanceTransactionStore;
import software.amazon.awssdk.enhanced.dynamodb.*;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.dynamodb.services.local.embedded.DynamoDBEmbedded;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StripeBalanceTransactionStoreTest {
    private final String TABLE_NAME = "StripeBalanceTransactions";

    private StripeBalanceTransactionStore stripeBalanceTransactionStore;

    @BeforeEach
    public void setup() {
        final DynamoDbEnhancedClient dynamoDbEnhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(DynamoDBEmbedded.create().dynamoDbClient())
                .build();
        final DynamoDbTable<BalanceTransaction> balanceTransactionTable =
                dynamoDbEnhancedClient.table(TABLE_NAME, TableSchema.fromBean(BalanceTransaction.class));
        balanceTransactionTable.createTable();
        final TableSchema<EnhancedDocument> enhancedDocumentTableSchema = TableSchema.documentSchemaBuilder()
                .addIndexPartitionKey(TableMetadata.primaryIndexName(), "id", AttributeValueType.S)
                .addIndexPartitionKey("Source","source", AttributeValueType.S)
                .attributeConverterProviders(AttributeConverterProvider.defaultProvider())
                .build();
        final DynamoDbTable<EnhancedDocument> enhancedDocumentTable =
                dynamoDbEnhancedClient.table(TABLE_NAME, enhancedDocumentTableSchema);
        stripeBalanceTransactionStore =
                new DynamoDbStripeBalanceTransactionStore(enhancedDocumentTable, new ObjectMapper());
    }

    @Test
    public void retrievesBalanceTransactionById() {
        final String id = UUID.randomUUID().toString();
        final String sourceId = UUID.randomUUID().toString();
        final org.seattleoba.data.model.stripe.BalanceTransaction balanceTransaction =
                new org.seattleoba.data.model.stripe.BalanceTransaction(id, sourceId);
        stripeBalanceTransactionStore.updateBalanceTransaction(balanceTransaction);

        final org.seattleoba.data.model.stripe.BalanceTransaction output =
                stripeBalanceTransactionStore.getBalanceTransactionById(id);

        assertEquals(balanceTransaction, output);
    }

    @Test
    public void retrievesBalanceTransactionBySourceId() {
        final String id = UUID.randomUUID().toString();
        final String sourceId = UUID.randomUUID().toString();
        final org.seattleoba.data.model.stripe.BalanceTransaction balanceTransaction =
                new org.seattleoba.data.model.stripe.BalanceTransaction(id, sourceId);
        stripeBalanceTransactionStore.updateBalanceTransaction(balanceTransaction);

        final org.seattleoba.data.model.stripe.BalanceTransaction output =
                stripeBalanceTransactionStore.getBalanceTransactionBySourceId(sourceId);

        assertEquals(balanceTransaction, output);
    }
}
