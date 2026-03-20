package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentIntent(
        @JsonProperty("id") String id,
        @JsonProperty("charge") String charge,
        @JsonProperty("customer") String customer,
        @JsonProperty("description") String description) {
}
