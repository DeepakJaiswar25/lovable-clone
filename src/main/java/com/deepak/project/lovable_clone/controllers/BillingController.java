package com.deepak.project.lovable_clone.controllers;

import com.deepak.project.lovable_clone.dto.subscription.*;
import com.deepak.project.lovable_clone.service.PlanService;
import com.deepak.project.lovable_clone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class BillingController {

    private final PlanService planService;
    private final SubscriptionService subscriptionService;

    @GetMapping("/api/plans")
    public ResponseEntity<List<PlanResponse>> getAllPlans(){

        return ResponseEntity.ok(planService.getALlActivePlans());
    }


    @GetMapping("/api/me/subscription")
    public ResponseEntity<List<SubscriptionResponse>> getAllSubscriptions(){
        Long userId=1L;
        return ResponseEntity.ok(subscriptionService.getCurrentSubscription(userId));
    }

    @PostMapping("/api/stripe/checkout")
    public ResponseEntity<CheckoutResponse> createCheckoutResponse(@RequestBody CheckoutRequest request){

        Long userId = 1L;
        return ResponseEntity.status(HttpStatus.CREATED).body(subscriptionService.getCheckOutResponse(request,userId));
    }

    @PostMapping("/api/stripe/portal")
    public ResponseEntity<PortalResponse> openCustomerPortal(){
        Long userId=1L;
        return ResponseEntity.ok(subscriptionService.openCustomerPortal(userId));
    }
}
