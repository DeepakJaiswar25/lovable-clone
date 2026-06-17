package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.subscription.PlanLimitsResponse;
import com.deepak.project.lovable_clone.dto.subscription.PlanResponse;
import com.deepak.project.lovable_clone.dto.subscription.SubscriptionResponse;
import com.deepak.project.lovable_clone.dto.subscription.UsageTodayResponse;
import com.deepak.project.lovable_clone.entity.UsageLog;
import com.deepak.project.lovable_clone.repository.UsageLogRepository;
import com.deepak.project.lovable_clone.security.AuthUtil;
import com.deepak.project.lovable_clone.service.SubscriptionService;
import com.deepak.project.lovable_clone.service.UsageService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UsageServiceImpl implements UsageService {

    private final UsageLogRepository usageLogRepository;
    private final AuthUtil authUtil;
    private final SubscriptionService subscriptionService;

    @Override
    public void recordTokenUsage(Long userId, int actualTokens) {

        LocalDate today = LocalDate.now();
        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today).
                orElseGet(() -> createNewDailyLog(userId, today));
        todayLog.setTokensUsed(todayLog.getTokensUsed() + actualTokens);
        usageLogRepository.save(todayLog);

    }

    @Override
    public void checkDailyTokensUsage() {

        Long userId = authUtil.getCurrentUserId();
        SubscriptionResponse subscriptionResponse= subscriptionService.getCurrentSubscription();
        PlanResponse plan = subscriptionResponse.plan();
        LocalDate today = LocalDate.now();
        if(plan.unlimitedAi()) return;
        UsageLog todayLog = usageLogRepository.findByUserIdAndDate(userId, today).
                orElseGet(() -> createNewDailyLog(userId, today));
        int currentUsage= todayLog.getTokensUsed();
        int limit = plan.maxTokensPerDay();

        if(currentUsage >= limit){
            throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,
                    "Daily limit reached, Upgrade now");
        }
    }

    private UsageLog createNewDailyLog(Long userId, LocalDate date) {
        UsageLog newLog = UsageLog.builder()
                .userId(userId)
                .date(date)
                .tokensUsed(0)
                .build();
        return usageLogRepository.save(newLog);
    }
}
