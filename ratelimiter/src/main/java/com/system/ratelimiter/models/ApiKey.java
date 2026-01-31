package com.system.ratelimiter.models;

import jakarta.persistence.*;

@Entity
@Table(name = "api_keys")
public class ApiKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "owner_name", nullable = false)
    private String ownerName;

    @Column(name = "api_key", nullable = false, unique = true)
    private String apiKey;

    @Column(name = "rate_limit", nullable = false)
    private Integer rateLimit;

    @Column(name = "window_seconds", nullable = false)
    private Integer windowSeconds;

    public ApiKey() {

    }

    public ApiKey(String ownerName, String apiKey, Integer rateLimit, Integer windowSeconds) {
        this.ownerName = ownerName;
        this.apiKey = apiKey;
        this.rateLimit = rateLimit;
        this.windowSeconds = windowSeconds;
    }

    public Long getId() {
        return id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public Integer getRateLimit() {
        return rateLimit;
    }

    public void setRateLimit(Integer rateLimit) {
        this.rateLimit = rateLimit;
    }

    public Integer getWindowSeconds() {
        return windowSeconds;
    }

    public void setWindowSeconds(Integer windowSeconds) {
        this.windowSeconds = windowSeconds;
    }
}
