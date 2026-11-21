package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Subset of a Stripe Charge object, as returned by the Stripe API and stored in DynamoDB. */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Charge(
    @JsonProperty("id") String id,
    @JsonProperty("amount") Long amount,
    @JsonProperty("balance_transaction") String balanceTransaction,
    @JsonProperty("captured") Boolean captured,
    @JsonProperty("created") Long created,
    @JsonProperty("currency") String currency,
    @JsonProperty("customer") String customer,
    @JsonProperty("description") String description,
    @JsonProperty("disputed") Boolean disputed,
    @JsonProperty("failure_balance_transaction") String failureBalanceTransaction,
    @JsonProperty("metadata") Map<String, String> metadata,
    @JsonProperty("payment_intent") String paymentIntent,
    @JsonProperty("payment_method") String paymentMethod,
    @JsonProperty("receipt_url") String receiptUrl,
    @JsonProperty("refunded") Boolean refunded,
    @JsonProperty("status") String status) {}
