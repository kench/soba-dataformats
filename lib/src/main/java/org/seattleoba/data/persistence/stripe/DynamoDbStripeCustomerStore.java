package org.seattleoba.data.persistence.stripe;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.seattleoba.data.model.stripe.Customer;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.document.EnhancedDocument;
import software.amazon.awssdk.enhanced.dynamodb.model.Page;
import software.amazon.awssdk.enhanced.dynamodb.model.QueryConditional;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Optional;

public class DynamoDbStripeCustomerStore implements StripeCustomerStore {
    private static final String EMAIL_INDEX = "Email";
    private static final String NAME_INDEX = "Name";

    private final DynamoDbTable<EnhancedDocument> dynamoDbTable;
    private final ObjectMapper objectMapper;

    @Inject
    public DynamoDbStripeCustomerStore(
            @Named("StripeCustomers") final DynamoDbTable<EnhancedDocument> dynamoDbTable,
            final ObjectMapper objectMapper) {
        this.dynamoDbTable = dynamoDbTable;
        this.objectMapper = objectMapper;
    }

    @Override
    public Customer getCustomerById(final String customerId) {
        final EnhancedDocument enhancedDocument = dynamoDbTable.getItem(Key.builder()
                .partitionValue(customerId)
                .build());
        return getCustomer(enhancedDocument);
    }

    @Override
    public Customer findCustomerByEmail(final String email) {
        return queryByIndex(EMAIL_INDEX, email);
    }

    @Override
    public Customer findCustomerByName(final String name) {
        return queryByIndex(NAME_INDEX, name);
    }

    @Override
    public void updateCustomer(final Customer customer) {
        final EnhancedDocument enhancedDocument;
        try {
            enhancedDocument = EnhancedDocument.builder()
                    .json(objectMapper.writeValueAsString(customer))
                    .build();
        } catch (final JsonProcessingException exception) {
            throw new IllegalArgumentException(exception);
        }
        dynamoDbTable.putItem(enhancedDocument);
    }

    private Customer queryByIndex(final String indexName, final String partitionValue) {
        final QueryConditional queryConditional = QueryConditional.keyEqualTo(Key.builder()
                .partitionValue(partitionValue)
                .build());
        final Optional<Page<EnhancedDocument>> page =
                dynamoDbTable.index(indexName)
                        .query(q -> q.queryConditional(queryConditional)).stream().findAny();
        return page.map(enhancedDocumentPage -> getCustomer(enhancedDocumentPage.items().getFirst())).orElse(null);
    }

    private Customer getCustomer(final EnhancedDocument enhancedDocument) {
        try {
            return objectMapper.readValue(enhancedDocument.toJson(), Customer.class);
        } catch (final JsonProcessingException exception) {
            throw new IllegalStateException(exception);
        }
    }
}
