package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.BalanceTransaction;

public interface StripeBalanceTransactionStore {
    BalanceTransaction getBalanceTransactionById(String balanceTransactionId);
    BalanceTransaction getBalanceTransactionBySourceId(String sourceId);
    void updateBalanceTransaction(BalanceTransaction balanceTransaction);
}
