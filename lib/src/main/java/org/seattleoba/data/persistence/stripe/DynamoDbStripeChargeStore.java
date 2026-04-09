package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seattleoba.data.model.stripe.Charge;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import javax.inject.Named;
import java.util.Collection;
import java.util.stream.Collectors;

public class DynamoDbStripeChargeStore implements StripeChargeStore {
    private static final String CUSTOMER_INDEX_NAME = "Customer";
    private static final String PAYMENT_INTENT_INDEX_NAME = "PaymentIntent";

    private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
    private final ObjectMapper objectMapper;

    public DynamoDbStripeChargeStore(
            @Named("StripeCharges") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
            final ObjectMapper objectMapper) {
            this.dynamoDbTable = dynamoDbTable;
            this.objectMapper = objectMapper;
    }

    @Override
    public Charge getChargeById(final String chargeId) {
        final EnhancedDocument enhancedDocument = dynamoDbTable.getItem(Key.builder()
                .partitionValue(chargeId)
                .build());
        return getCharge(enhancedDocument);
    }

    @Override
    public Collection<Charge> findChargesByCustomerId(final String customerId) {
        return queryByIndex(CUSTOMER_INDEX_NAME, customerId);
    }

    @Override
    public Collection<Charge> findChargesByPaymentIntentId(final String paymentIntentId) {
        return queryByIndex(PAYMENT_INTENT_INDEX_NAME, paymentIntentId);
    }

    @Override
    public void updateCharge(final Charge charge) {
        final EnhancedDocument enhancedDocument;
        try {
            enhancedDocument = EnhancedDocument.builder()
                    .json(objectMapper.writeValueAsString(charge))
                    .build();
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException(exception);
        }
        dynamoDbTable.putItem(enhancedDocument);
    }

    private Collection<Charge> queryByIndex(final String indexName, final String partitionValue) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(partitionValue)
                .build());
        return dynamoDbTable.index(indexName)
                .query(q -> q.queryConditional(queryConditional)).stream()
                .flatMap(page -> page.items().stream())
                .map(this::getCharge)
                .collect(Collectors.toList());
    }

    private Charge getCharge(final EnhancedDocument enhancedDocument) {
        try {
            return objectMapper.readValue(enhancedDocument.toJson(), Charge.class);
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
