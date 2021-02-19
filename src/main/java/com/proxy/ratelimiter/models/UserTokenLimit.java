package com.proxy.ratelimiter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USER_TOKEN_LIMIT")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserTokenLimit {

    @Id
    private String clientId;
    private String token;
    private Long rateLimit;
    private String salt;
    private Long createdOn;

}
