package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seattleoba.data.model.stripe.BalanceTransaction;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

public class DynamoDbStripeBalanceTransactionStore implements StripeBalanceTransactionStore {
    private static final String SOURCE_INDEX = "Source";

    private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
    private final ObjectMapper objectMapper;

    @Inject
    public DynamoDbStripeBalanceTransactionStore(
            @Named("StripeBalanceTransactions") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
            final ObjectMapper objectMapper) {
        this.dynamoDbTable = dynamoDbTable;
        this.objectMapper = objectMapper;
    }

    @Override
    public BalanceTransaction getBalanceTransactionById(final String balanceTransactionId) {
        final EnhancedDocument enhancedDocument = dynamoDbTable.getItem(Key.builder()
                .partitionValue(balanceTransactionId)
                .build());
        return getBalanceTransaction(enhancedDocument);
    }

    @Override
    public BalanceTransaction getBalanceTransactionBySourceId(final String sourceId) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(sourceId)
                .build());
        final Optional<Page<EnhancedDocument>> page =
                dynamoDbTable.index(SOURCE_INDEX)
                        .query(q -> q.queryConditional(queryConditional)).stream().findAny();
        return page.map(enhancedDocumentPage -> getBalanceTransaction(enhancedDocumentPage.items().getFirst())).orElse(null);
    }

    @Override
    public void updateBalanceTransaction(final BalanceTransaction balanceTransaction) {
        final EnhancedDocument enhancedDocument;
        try {
            enhancedDocument = EnhancedDocument.builder()
                    .json(objectMapper.writeValueAsString(balanceTransaction))
                    .build();
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException(exception);
        }
        dynamoDbTable.updateItem(enhancedDocument);
    }

    private BalanceTransaction getBalanceTransaction(final EnhancedDocument enhancedDocument) {
        try {
            return objectMapper.readValue(enhancedDocument.toJson(), BalanceTransaction.class);
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
