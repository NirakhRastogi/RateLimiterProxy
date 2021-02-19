package com.proxy.ratelimiter.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/ping")
    public String ping() throws InterruptedException {
        Thread.sleep(1000);
        return "pong";
    }

}
