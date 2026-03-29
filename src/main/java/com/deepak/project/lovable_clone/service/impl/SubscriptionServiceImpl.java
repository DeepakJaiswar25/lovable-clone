package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.subscription.CheckoutRequest;
import com.deepak.project.lovable_clone.dto.subscription.CheckoutResponse;
import com.deepak.project.lovable_clone.dto.subscription.PortalResponse;
import com.deepak.project.lovable_clone.dto.subscription.SubscriptionResponse;
import com.deepak.project.lovable_clone.entity.Plan;
import com.deepak.project.lovable_clone.entity.Subscription;
import com.deepak.project.lovable_clone.entity.User;
import com.deepak.project.lovable_clone.enums.SubscriptionStatus;
import com.deepak.project.lovable_clone.error.ResourceNotFoundException;
import com.deepak.project.lovable_clone.mapper.SubscriptionMapper;
import com.deepak.project.lovable_clone.repository.PlanRepository;
import com.deepak.project.lovable_clone.repository.SubscriptionRepository;
import com.deepak.project.lovable_clone.repository.UserRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final AuthUtil authUtil;
    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepository userRepository;
    private final PlanRepository planRepository;


    @Override
    public SubscriptionResponse getCurrentSubscription() {
        Long userId = authUtil.getCurrentUserId();
        var currentSubscription = subscriptionRepository.findByUserIdAndStatusIn(userId,
                Set.of(SubscriptionStatus.ACTIVE,SubscriptionStatus.PAST_DUE,SubscriptionStatus.TRIALING))
                .orElse(new Subscription());

        return subscriptionMapper.toSubscriptionResponse(currentSubscription);
    }

    @Override
    public void activateSubscription(Long userId, Long planId, String subscriptionId, String customerId) {

        Boolean exists= subscriptionRepository.existsByStripeSubscriptionId(subscriptionId);
        if (exists) {
            return;
        }

        User user =  getUser(userId);
        Plan plan = getPlan(planId);

        Subscription subscription= Subscription.builder()
                .stripeSubscriptionId(subscriptionId)
                .plan(plan)
                .user(user)
                .status(SubscriptionStatus.INCOMPLETE)
                .build();

        subscriptionRepository.save(subscription);


    }

    @Override
    public void updateSubscription(String id, SubscriptionStatus status, Instant periodStart, Instant periodEnd, Boolean cancelAtPeriodEnd, Long planId) {

    }

    @Override
    public void cancelSubscription(String id) {

    }

    @Override
    public void renewSubscriptionPeriod(String subId, Instant periodStart, Instant periodEnd) {

        Subscription subscription = getSubscription(subId);

        Instant newStart= periodStart!=null ? periodStart : subscription.getCurrentPeriodStart();
        subscription.setCurrentPeriodStart(newStart);
        subscription.setCurrentPeriodEnd(periodEnd);

        if (subscription.getStatus()==SubscriptionStatus.INCOMPLETE || subscription.getStatus()==SubscriptionStatus.PAST_DUE) {
            subscription.setStatus(SubscriptionStatus.ACTIVE);
        }

        subscriptionRepository.save(subscription);

    }

    @Override
    public void markSubscriptionPastDue(String subId) {

    }


    //Utility Methods

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", userId.toString()));
    }

    private Plan getPlan(Long planId) {
        return planRepository.findById(planId)
                .orElseThrow(() -> new ResourceNotFoundException("Plan", planId.toString()));

    }

    private Subscription getSubscription(String gatewaySubscriptionId) {
        return subscriptionRepository.findByStripeSubscriptionId(gatewaySubscriptionId).orElseThrow(() ->
                new ResourceNotFoundException("Subscription", gatewaySubscriptionId));
    }

}
