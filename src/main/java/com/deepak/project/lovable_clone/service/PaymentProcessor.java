package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.deepak.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.deepak.project.lovable_clone.dto.subscription.PortalResponse;
import com.stripe.model.StripeObject;

import java.util.Map;

public interface PaymentProcessor {

    CheckoutResponse getCheckOutResponse(CheckoutRequest request);

    PortalResponse openCustomerPortal();

    void handleWebhookEvent(String type, StripeObject stripeObject, Map<String, String> metadata);
}
