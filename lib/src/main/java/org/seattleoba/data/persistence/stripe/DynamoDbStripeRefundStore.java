package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seattleoba.data.model.stripe.Refund;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import javax.inject.Named;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class DynamoDbStripeRefundStore implements StripeRefundStore {
    private static final String CHARGE_INDEX_NAME = "Charge";
    private static final String PAYMENT_INTENT_INDEX_NAME = "PaymentIntent";

    private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
    private final ObjectMapper objectMapper;

    public DynamoDbStripeRefundStore(
            @Named("StripeRefunds") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
            final ObjectMapper objectMapper) {
        this.dynamoDbTable = dynamoDbTable;
        this.objectMapper = objectMapper;
    }

    @Override
    public Refund getRefundById(final String refundId) {
        final EnhancedDocument enhancedDocument = dynamoDbTable.getItem(Key.builder()
                .partitionValue(refundId)
                .build());
        return getRefund(enhancedDocument);
    }

    @Override
    public Collection<Refund> findRefundsByChargeId(final String chargeId) {
        return queryByIndex(CHARGE_INDEX_NAME, chargeId);
    }

    @Override
    public Collection<Refund> findRefundsByPaymentIntentId(final String paymentIntentId) {
        return queryByIndex(PAYMENT_INTENT_INDEX_NAME, paymentIntentId);
    }

    @Override
    public void updateRefund(final Refund refund) {
        final EnhancedDocument enhancedDocument;
        try {
            enhancedDocument = EnhancedDocument.builder()
                    .json(objectMapper.writeValueAsString(refund))
                    .build();
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException(exception);
        }
        dynamoDbTable.putItem(enhancedDocument);
    }

    private Collection<Refund> queryByIndex(final String indexName, final String partitionValue) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(partitionValue)
                .build());
        return dynamoDbTable.index(indexName)
                .query(q -> q.queryConditional(queryConditional)).stream()
                .flatMap(page -> page.items().stream())
                .map(this::getRefund)
                .collect(Collectors.toList());
    }

    private Refund getRefund(final EnhancedDocument enhancedDocument) {
        try {
            return objectMapper.readValue(enhancedDocument.toJson(), Refund.class);
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
