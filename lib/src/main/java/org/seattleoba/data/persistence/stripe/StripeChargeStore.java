package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.Charge;

import java.util.Collection;

public interface StripeChargeStore {
    Charge getChargeById(String chargeId);
    Collection<Charge> findChargesByCustomerId(String customerId);
    Collection<Charge> findChargesByPaymentIntentId(String paymentIntentId);
    void updateCharge(Charge charge);
}
