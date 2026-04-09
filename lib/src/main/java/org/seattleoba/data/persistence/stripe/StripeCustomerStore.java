package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.Customer;

public interface StripeCustomerStore {
    Customer getCustomerById(String customerId);
    Customer findCustomerByEmail(String email);
    Customer findCustomerByName(String name);
    void updateCustomer(Customer customer);
}
