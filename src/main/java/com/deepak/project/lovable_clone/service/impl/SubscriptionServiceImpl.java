package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.deepak.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.deepak.project.lovable_clone.dto.subscription.PortalResponse;
import com.deepak.project.lovable_clone.dto.subscription.SubscriptionResponse;
import com.deepak.project.lovable_clone.service.SubscriptionService;

import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    @Override
    public List<SubscriptionResponse> getCurrentSubscription(Long userId) {
        return List.of();
    }

    @Override
    public CheckoutResponse getCheckOutResponse(CheckoutRequest request, Long userId) {
        return null;
    }

    @Override
    public PortalResponse openCustomerPortal(Long userId) {
        return null;
    }
}
