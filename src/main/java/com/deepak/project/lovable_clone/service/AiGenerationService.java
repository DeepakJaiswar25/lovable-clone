package com.deepak.project.lovable_clone.service;

import aj.org.objectweb.asm.commons.Remapper;
import com.deepak.project.lovable_clone.dto.chat.StreamResponse;
import reactor.core.publisher.Flux;

public interface AiGenerationService {


    Flux<StreamResponse> streamResponse(String message, Long projectId);
}
