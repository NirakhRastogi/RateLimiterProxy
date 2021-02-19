package com.proxy.ratelimiter.service;

import com.proxy.ratelimiter.models.RequestDetail;
import com.proxy.ratelimiter.repository.RequestDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RequestDetailService {

    @Autowired
    private RequestDetailRepository requestDetailRepository;

    @Async
    public void insertRequestDetail(RequestDetail requestDetail) {
        this.requestDetailRepository.save(requestDetail);
    }

}
