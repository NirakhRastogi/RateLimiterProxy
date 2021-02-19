package com.proxy.ratelimiter.util;

import com.proxy.ratelimiter.models.RequestDetail;

import java.util.UUID;

public class RequestDetailCreator {

    public static RequestDetail createRequestDetail(String clientId, String method, Long timestamp, Long ttr, String uri) {
        return RequestDetail.builder()
                .id(UUID.randomUUID().toString())
                .clientId(clientId)
                .method(method)
                .timestamp(timestamp)
                .ttr(ttr)
                .uri(uri)
                .build();
    }

}
