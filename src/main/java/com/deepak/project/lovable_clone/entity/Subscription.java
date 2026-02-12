package com.deepak.project.lovable_clone.entity;

import com.deepak.project.lovable_clone.enums.SubscriptionStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.Instant;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
public class Subscription {

    Long id;
    User user;
    Plan plan;
    String stripeSubscriptionId;
    String stripeCustomerId;
    SubscriptionStatus status;
    Instant currentPeriodStart;
    Instant currentPeriodEnd;
    Boolean cancelAtPeriodEnd;
    Instant createdAt;
    Instant updatedAt;



}
