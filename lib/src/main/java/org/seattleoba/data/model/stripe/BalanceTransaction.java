package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BalanceTransaction(
        @JsonProperty("id") String id,
        @JsonProperty("source") String source) {
}
