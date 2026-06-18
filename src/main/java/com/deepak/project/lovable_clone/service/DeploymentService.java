package com.deepak.project.lovable_clone.service;

import com.deepak.project.lovable_clone.dto.deploy.DeployResponse;

public interface DeploymentService {


    DeployResponse deploy(Long projectId);
}
