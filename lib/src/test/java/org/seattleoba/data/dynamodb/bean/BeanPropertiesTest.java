package org.seattleoba.data.dynamodb.bean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

/** Checks that every DynamoDB bean property reads back what was written. */
class BeanPropertiesTest {
  private static final Map<Class<?>, Object> SAMPLE_VALUES =
      Map.of(
          String.class,
          "value",
          Integer.class,
          42,
          Long.class,
          1_700_000_000L,
          BigDecimal.class,
          new BigDecimal("12.34"));

  static Stream<Class<?>> beans() {
    return Stream.of(
        BarcodeScan.class,
        BevyOrder.class,
        BevyTicket.class,
        EventRegistration.class,
        TwitchAccount.class,
        TwitchTeam.class,
        TwitchTeamMembership.class,
        org.seattleoba.data.dynamodb.bean.stripe.BalanceTransaction.class,
        org.seattleoba.data.dynamodb.bean.stripe.Charge.class,
        org.seattleoba.data.dynamodb.bean.stripe.Customer.class,
        org.seattleoba.data.dynamodb.bean.stripe.PaymentIntent.class,
        org.seattleoba.data.dynamodb.bean.stripe.Refund.class);
  }

  @ParameterizedTest
  @MethodSource("beans")
  void propertiesRoundTrip(final Class<?> beanClass)
      throws IntrospectionException, ReflectiveOperationException {
    final Object bean = beanClass.getConstructor().newInstance();
    int checked = 0;
    for (final PropertyDescriptor property :
        Introspector.getBeanInfo(beanClass, Object.class).getPropertyDescriptors()) {
      final Object value = SAMPLE_VALUES.get(property.getPropertyType());
      assertNotNull(value, "no sample value for " + property.getName());
      property.getWriteMethod().invoke(bean, value);
      assertEquals(value, property.getReadMethod().invoke(bean), property.getName());
      checked++;
    }
    assertTrue(checked > 0, "bean has no properties");
  }

  @ParameterizedTest
  @MethodSource("beans")
  void ttlDefaultsToOneDayAhead(final Class<?> beanClass)
      throws IntrospectionException, InvocationTargetException, ReflectiveOperationException {
    final Object bean = beanClass.getConstructor().newInstance();
    for (final PropertyDescriptor property :
        Introspector.getBeanInfo(beanClass, Object.class).getPropertyDescriptors()) {
      if (property.getName().equals("ttl")) {
        final long ttl = (Long) property.getReadMethod().invoke(bean);
        final long oneDayAhead = Instant.now().getEpochSecond() + 86_400;
        assertTrue(Math.abs(ttl - oneDayAhead) < 60, "ttl should be about one day ahead");
      }
    }
  }
}
