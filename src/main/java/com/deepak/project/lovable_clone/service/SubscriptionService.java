package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.deepak.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.deepak.project.lovable_clone.dto.subscription.PortalResponse;
import com.deepak.project.lovable_clone.dto.subscription.SubscriptionResponse;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionResponse> getCurrentSubscription(Long userId);

    CheckoutResponse getCheckOutResponse(CheckoutRequest request, Long userId);

    PortalResponse openCustomerPortal(Long userId);
}
