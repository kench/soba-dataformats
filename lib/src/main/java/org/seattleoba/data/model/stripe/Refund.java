package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record Refund(
        @JsonProperty("id") String id,
        @JsonProperty("charge") String charge,
        @JsonProperty("payment_intent") String paymentIntent) {
}
