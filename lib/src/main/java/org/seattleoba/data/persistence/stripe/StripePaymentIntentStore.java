package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.PaymentIntent;

/** Persistence layer for Stripe payment intents. */
public interface StripePaymentIntentStore {
  /**
   * Returns the payment intent with the given ID.
   *
   * @param paymentIntentId Stripe payment intent ID ({@code pi_...})
   * @return the payment intent
   */
  PaymentIntent getPaymentIntentById(String paymentIntentId);

  /**
   * Returns the payment intent with the given description, such as a Bevy order number.
   *
   * @param description payment intent description
   * @return the payment intent, or {@code null} if there is none
   */
  PaymentIntent findPaymentIntentByDescription(String description);

  /**
   * Returns the payment intent whose latest charge is the given charge.
   *
   * @param chargeId Stripe charge ID ({@code ch_...})
   * @return the payment intent, or {@code null} if there is none
   */
  PaymentIntent findPaymentIntentByLatestCharge(String chargeId);

  /**
   * Creates or replaces a payment intent.
   *
   * @param paymentIntent the payment intent to store
   */
  void updatePaymentIntent(PaymentIntent paymentIntent);
}
