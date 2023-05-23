package com.muh.binancerequests.service;

import org.springframework.beans.factory.annotation.Value;

public class ApiKey {
    @Value("${key}")
    private static String key;
    private String secret;

    public ApiKey(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

}
