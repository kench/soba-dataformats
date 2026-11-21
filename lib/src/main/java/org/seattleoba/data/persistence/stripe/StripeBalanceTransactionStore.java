package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.BalanceTransaction;

/** Persistence layer for Stripe balance transactions. */
public interface StripeBalanceTransactionStore {
  /**
   * Returns the balance transaction with the given ID.
   *
   * @param balanceTransactionId Stripe balance transaction ID ({@code txn_...})
   * @return the balance transaction
   */
  BalanceTransaction getBalanceTransactionById(String balanceTransactionId);

  /**
   * Returns the balance transaction created by a source object, such as a charge or refund.
   *
   * @param sourceId Stripe ID of the source object
   * @return the balance transaction, or {@code null} if there is none
   */
  BalanceTransaction getBalanceTransactionBySourceId(String sourceId);

  /**
   * Creates or replaces a balance transaction.
   *
   * @param balanceTransaction the balance transaction to store
   */
  void updateBalanceTransaction(BalanceTransaction balanceTransaction);
}
