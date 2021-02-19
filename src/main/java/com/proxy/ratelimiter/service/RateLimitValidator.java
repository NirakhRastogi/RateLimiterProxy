package com.proxy.ratelimiter.service;

import com.proxy.ratelimiter.models.UserTokenLimit;
import com.proxy.ratelimiter.repository.UserHitsCountCacheImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RateLimitValidator {

    private final UserHitsCountCacheImpl userHitsCountCache;

    public boolean validateAndUpdateRateLimit(UserTokenLimit userTokenLimit) {
        String clientId = userTokenLimit.getClientId();
        Long rateLimit = userTokenLimit.getRateLimit();
        Long currentHitCount = this.userHitsCountCache.getClientHitCount(clientId);
        if (currentHitCount > rateLimit) {
            throw new IllegalStateException("You have used your hourly quota! If you want to use our service, please request to increase your rate limit quota.");
        }
        this.userHitsCountCache.updateClientHitCount(clientId, currentHitCount + 1);
        return false;
    }

}
