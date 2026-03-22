package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seattleoba.data.model.stripe.PaymentIntent;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

public class DynamoDbStripePaymentIntentStore implements StripePaymentIntentStore{
    private static final String CHARGE_INDEX = "Charge";
    private static final String CUSTOMER_INDEX = "Customer";
    private static final String DESCRIPTION_INDEX = "Description";

    private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
    private final ObjectMapper objectMapper;

    @Inject
    public DynamoDbStripePaymentIntentStore(
            @Named("StripePaymentIntents") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
            final ObjectMapper objectMapper) {
        this.dynamoDbTable = dynamoDbTable;
        this.objectMapper = objectMapper;
    }

    @Override
    public PaymentIntent getPaymentIntentById(final String paymentIntentId) {
        final EnhancedDocument enhancedDocument = dynamoDbTable.getItem(Key.builder()
                .partitionValue(paymentIntentId)
                .build());
        return getPaymentIntent(enhancedDocument);
    }

    @Override
    public PaymentIntent findPaymentIntentByDescription(final String description) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(description)
                .build());
        final Optional<Page<EnhancedDocument>> page =
                dynamoDbTable.index(DESCRIPTION_INDEX)
                        .query(q -> q.queryConditional(queryConditional)).stream().findAny();
        return page.map(enhancedDocumentPage -> getPaymentIntent(enhancedDocumentPage.items().getFirst())).orElse(null);
    }

    @Override
    public PaymentIntent findPaymentIntentByLatestCharge(final String chargeId) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(chargeId)
                .build());
        final Optional<Page<EnhancedDocument>> page =
                dynamoDbTable.index(CHARGE_INDEX)
                        .query(q -> q.queryConditional(queryConditional)).stream().findAny();
        return page.map(enhancedDocumentPage -> getPaymentIntent(enhancedDocumentPage.items().getFirst())).orElse(null);
    }

    @Override
    public void updatePaymentIntent(final PaymentIntent paymentIntent) {
        final EnhancedDocument enhancedDocument;
        try {
            enhancedDocument = EnhancedDocument.builder()
                    .json(objectMapper.writeValueAsString(paymentIntent))
                    .build();
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException(exception);
        }
        dynamoDbTable.putItem(enhancedDocument);
    }

    private PaymentIntent getPaymentIntent(final EnhancedDocument enhancedDocument) {
        try {
            return objectMapper.readValue(enhancedDocument.toJson(), PaymentIntent.class);
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
