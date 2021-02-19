package com.proxy.ratelimiter.repository;

import com.proxy.ratelimiter.models.RequestDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestDetailRepository extends JpaRepository<RequestDetail, String> {

}
