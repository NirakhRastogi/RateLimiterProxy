package com.proxy.ratelimiter.controller;

import com.proxy.ratelimiter.dto.TokenRequest;
import com.proxy.ratelimiter.dto.TokenResponse;
import com.proxy.ratelimiter.service.UserTokenLimitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TokenController {

    private final UserTokenLimitService userTokenLimitService;

    @PostMapping("/register")
    public ResponseEntity<TokenResponse> ping(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(this.userTokenLimitService.generateAndSaveTokenForUser(tokenRequest));
    }

}
