package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Refund(
        @JsonProperty("id") String id,
        @JsonProperty("charge") String charge,
        @JsonProperty("payment_intent") String paymentIntent) {
}
