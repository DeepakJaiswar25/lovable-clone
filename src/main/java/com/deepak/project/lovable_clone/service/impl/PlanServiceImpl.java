package com.deepak.project.lovable_clone.service.impl;

import com.deepak.project.lovable_clone.dto.subscription.PlanResponse;
import com.deepak.project.lovable_clone.service.PlanService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PlanServiceImpl implements PlanService {
    @Override
    public List<PlanResponse> getALlActivePlans() {
        return List.of();
    }
}
