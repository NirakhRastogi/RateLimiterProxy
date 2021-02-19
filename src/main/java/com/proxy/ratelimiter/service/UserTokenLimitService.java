package com.proxy.ratelimiter.service;

import com.proxy.ratelimiter.dto.TokenRequest;
import com.proxy.ratelimiter.dto.TokenResponse;
import com.proxy.ratelimiter.models.UserTokenLimit;
import com.proxy.ratelimiter.repository.UserTokenLimitRepository;
import com.proxy.ratelimiter.util.HashUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserTokenLimitService {

    private final UserTokenLimitRepository userTokenLimitRepository;

    public TokenResponse generateAndSaveTokenForUser(TokenRequest tokenRequest) {
        String token = RandomStringUtils.randomAlphanumeric(15);
        String salt = RandomStringUtils.randomAlphanumeric(6);
        String hashToken = HashUtil.hashData(token, salt);

        UserTokenLimit tokenResponse = this.userTokenLimitRepository.save(UserTokenLimit.builder()
                .rateLimit(tokenRequest.getRateLimit())
                .clientId(tokenRequest.getClientId())
                .salt(salt)
                .token(hashToken)
                .createdOn(System.currentTimeMillis())
                .build());

        return TokenResponse.builder()
                .clientId(tokenRequest.getClientId())
                .token(token)
                .createdOn(tokenResponse.getCreatedOn())
                .build();
    }

    public UserTokenLimit validateToken(String clientId, String token) {
        Optional<UserTokenLimit> userLimitRecord = this.userTokenLimitRepository.findById(clientId);
        if (!userLimitRecord.isPresent()) {
            throw new IllegalStateException("User requested with invalid client id, " + clientId);
        }
        UserTokenLimit userTokenLimit = userLimitRecord.get();
        if (!userTokenLimit.getToken().equals(HashUtil.hashData(token, userTokenLimit.getSalt()))) {
            throw new IllegalStateException("Client with id " + clientId + " requested with invalid api-token");
        }
        return userTokenLimit;
    }

}
