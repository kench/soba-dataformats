package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Refund(
        @JsonProperty("id") String id,
        @JsonProperty("balance_transaction") String balanceTransaction,
        @JsonProperty("charge") String charge,
        @JsonProperty("payment_intent") String paymentIntent,
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("description") String description,
        @JsonProperty("metadata") Map<String, String> metadata,
        @JsonProperty("reason") String reason,
        @JsonProperty("status") String status) {
}
