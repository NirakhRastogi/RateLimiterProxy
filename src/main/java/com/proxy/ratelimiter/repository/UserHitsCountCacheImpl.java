package com.proxy.ratelimiter.repository;


import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserHitsCountCacheImpl {

    private final HashOperations<String, String, Object> hashOperations;

    private static final String HIT_COUNT_KEY = "HIT_COUNT";

    public UserHitsCountCacheImpl(RedisTemplate<String, Object> redisTemplate) {
        this.hashOperations = redisTemplate.opsForHash();
    }

    public void updateClientHitCount(String clientId, Long hitCount) {
        this.hashOperations.put(HIT_COUNT_KEY, clientId, hitCount);
    }

    public Long getClientHitCount(String clientId) {
        if (this.hashOperations.hasKey(HIT_COUNT_KEY, clientId)) {
            return (Long) this.hashOperations.get(HIT_COUNT_KEY, clientId);
        }
        return 0L;
    }

    public void clearCache() {
        this.hashOperations.getOperations().delete(HIT_COUNT_KEY);
    }
}
