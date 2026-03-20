package org.seattleoba.data.model.stripe;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Customer(
        @JsonProperty("id") String id,
        @JsonProperty("email") String email,
        @JsonProperty("name") String name) {
}
