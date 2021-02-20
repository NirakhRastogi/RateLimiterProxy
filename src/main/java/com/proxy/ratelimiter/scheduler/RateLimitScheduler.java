package com.proxy.ratelimiter.scheduler;

import com.proxy.ratelimiter.repository.UserHitsCountCacheImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Component
public class RateLimitScheduler {

    @Autowired
    private UserHitsCountCacheImpl userHitsCountCache;

    @Autowired
    private ExecutorService executorService;

    @Scheduled(cron = "${rate-limit.reset.scheduler}")
    public void invalidateCache() {

        this.executorService.submit(() -> {
            this.userHitsCountCache.clearCache();
        });
    }

}
