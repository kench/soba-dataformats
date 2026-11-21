package org.seattleoba.data.persistence.stripe;

import org.seattleoba.data.model.stripe.Customer;

/** Persistence layer for Stripe customers. */
public interface StripeCustomerStore {
  /**
   * Returns the customer with the given ID.
   *
   * @param customerId Stripe customer ID ({@code cus_...})
   * @return the customer
   */
  Customer getCustomerById(String customerId);

  /**
   * Returns the customer with the given email address.
   *
   * @param email customer email address
   * @return the customer, or {@code null} if there is none
   */
  Customer findCustomerByEmail(String email);

  /**
   * Returns the customer with the given name.
   *
   * @param name customer name
   * @return the customer, or {@code null} if there is none
   */
  Customer findCustomerByName(String name);

  /**
   * Creates or replaces a customer.
   *
   * @param customer the customer to store
   */
  void updateCustomer(Customer customer);
}
