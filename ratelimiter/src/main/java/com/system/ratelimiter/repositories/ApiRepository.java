package com.system.ratelimiter.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import com.system.ratelimiter.models.ApiKey;

public interface ApiRepository extends JpaRepository<ApiKey, Long> {
    public ApiKey findByApiKey(String apiKey);
}
