package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public record PaymentIntent(
        @JsonProperty("id") String id,
        @JsonProperty("latest_charge") String latestCharge,
        @JsonProperty("customer") String customer,
        @JsonProperty("description") String description,
        @JsonProperty("amount") Long amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("metadata") Map<String, String> metadata,
        @JsonProperty("payment_method") String paymentMethod,
        @JsonProperty("receipt_email") String receiptEmail,
        @JsonProperty("status") String status,
        @JsonProperty("created") Long created,
        @JsonProperty("application") String application,
        @JsonProperty("application_fee_amount") Long applicationFeeAmount) {
}
