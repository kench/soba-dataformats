package org.seattleoba.data.persistence.stripe;

import java.util.Collection;
import org.seattleoba.data.model.stripe.Charge;

/** Persistence layer for Stripe charges. */
public interface StripeChargeStore {
  /**
   * Returns the charge with the given ID.
   *
   * @param chargeId Stripe charge ID ({@code ch_...})
   * @return the charge
   */
  Charge getChargeById(String chargeId);

  /**
   * Returns the charges made by a customer.
   *
   * @param customerId Stripe customer ID ({@code cus_...})
   * @return the matching charges, possibly empty
   */
  Collection<Charge> findChargesByCustomerId(String customerId);

  /**
   * Returns the charges made for a payment intent.
   *
   * @param paymentIntentId Stripe payment intent ID ({@code pi_...})
   * @return the matching charges, possibly empty
   */
  Collection<Charge> findChargesByPaymentIntentId(String paymentIntentId);

  /**
   * Creates or replaces a charge.
   *
   * @param charge the charge to store
   */
  void updateCharge(Charge charge);
}
