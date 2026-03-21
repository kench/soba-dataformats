package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentIntent(
        @JsonProperty("id") String id,
        @JsonProperty("latest_charge") String latestCharge,
        @JsonProperty("customer") String customer,
        @JsonProperty("description") String description) {
}
