package org.seattleoba.data.persistence.stripe;

import java.util.Collection;
import org.seattleoba.data.model.stripe.Refund;

/** Persistence layer for Stripe refunds. */
public interface StripeRefundStore {
  /**
   * Returns the refund with the given ID.
   *
   * @param refundId Stripe refund ID ({@code re_...})
   * @return the refund
   */
  Refund getRefundById(final String refundId);

  /**
   * Returns the refunds of a charge.
   *
   * @param chargeId Stripe charge ID ({@code ch_...})
   * @return the matching refunds, possibly empty
   */
  Collection<Refund> findRefundsByChargeId(final String chargeId);

  /**
   * Returns the refunds of a payment intent.
   *
   * @param paymentIntentId Stripe payment intent ID ({@code pi_...})
   * @return the matching refunds, possibly empty
   */
  Collection<Refund> findRefundsByPaymentIntentId(final String paymentIntentId);

  /**
   * Creates or replaces a refund.
   *
   * @param refund the refund to store
   */
  void updateRefund(Refund refund);
}
