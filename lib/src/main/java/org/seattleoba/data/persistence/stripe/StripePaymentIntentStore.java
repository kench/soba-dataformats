package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.PaymentIntent;

public interface StripePaymentIntentStore {
    PaymentIntent getPaymentIntentById(String paymentIntentId);
    PaymentIntent findPaymentIntentByDescription(String description);
    PaymentIntent findPaymentIntentByLatestCharge(String chargeId);
    void updatePaymentIntent(PaymentIntent paymentIntent);
}
