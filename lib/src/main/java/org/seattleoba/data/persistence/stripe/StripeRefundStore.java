package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.Refund;

import java.util.Collection;

public interface StripeRefundStore {
    Refund getRefundById(final String refundId);
    Collection<Refund> findRefundsByChargeId(final String chargeId);
    Collection<Refund> findRefundsByPaymentIntentId(final String paymentIntentId);
    void updateRefund(Refund refund);
}
