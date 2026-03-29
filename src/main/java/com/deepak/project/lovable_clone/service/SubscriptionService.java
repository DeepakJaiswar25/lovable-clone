package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.deepak.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.deepak.project.lovable_clone.dto.subscription.PortalResponse;
import com.deepak.project.lovable_clone.dto.subscription.SubscriptionResponse;
import com.deepak.project.lovable_clone.enums.SubscriptionStatus;

import java.time.Instant;
import java.util.List;

public interface SubscriptionService {
    SubscriptionResponse getCurrentSubscription();


    void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId);

    void updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId);

    void cancelSubscription(String id);

    void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd);

    void markSubscriptionPastDue(String subId);
}
