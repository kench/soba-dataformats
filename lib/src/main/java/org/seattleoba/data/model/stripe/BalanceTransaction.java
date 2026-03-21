package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BalanceTransaction(
        @JsonProperty("id") String id,
        @JsonProperty("amount") Integer amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("description") String description,
        @JsonProperty("fee") Integer fee,
        @JsonProperty("net") Integer net,
        @JsonProperty("source") String source,
        @JsonProperty("status") String status,
        @JsonProperty("type") String type) {
}
