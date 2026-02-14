package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.deepak.project.lovable_clone.dto.subscription.UsageTodayResponse;

public interface UsageService {
    UsageTodayResponse getUsageToday(Long userId);

    PlanLimitsResponse getPlanLimits(Long userId);
}
