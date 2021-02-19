package com.proxy.ratelimiter.repository;

import com.proxy.ratelimiter.models.UserTokenLimit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenLimitRepository extends JpaRepository<UserTokenLimit, String> {

}
