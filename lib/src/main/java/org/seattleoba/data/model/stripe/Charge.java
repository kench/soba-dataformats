package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Charge(
        @JsonProperty("id") String id,
        @JsonProperty("customer") String customer,
        @JsonProperty("payment_intent") String paymentIntent) {
}
