package com.proxy.ratelimiter.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "REQUEST_DETAILS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RequestDetail {

    @Id
    private String id;
    private String clientId;
    private String uri;
    private String method;
    private Long timestamp;
    private Long ttr;

}
