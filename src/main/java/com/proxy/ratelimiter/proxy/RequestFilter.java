package com.proxy.ratelimiter.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxy.ratelimiter.dto.ErrorResponse;
import com.proxy.ratelimiter.models.UserTokenLimit;
import com.proxy.ratelimiter.service.RateLimitValidator;
import com.proxy.ratelimiter.service.RequestDetailService;
import com.proxy.ratelimiter.service.UserTokenLimitService;
import com.proxy.ratelimiter.util.RequestDetailCreator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Order(0)
@Slf4j
@RequiredArgsConstructor
public class RequestFilter implements Filter {

    private final UserTokenLimitService userTokenLimitService;
    private final RateLimitValidator rateLimitValidator;
    private final RequestDetailService requestDetailService;

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        String clientId = httpServletRequest.getHeader("x-api-client-id");
        String userToken = httpServletRequest.getHeader("x-api-token");
        try {
            if ("/register".equals(httpServletRequest.getRequestURI()) && "POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                UserTokenLimit userTokenLimit = userTokenLimitService.validateToken(clientId, userToken);
                if (!rateLimitValidator.validateAndUpdateRateLimit(userTokenLimit)) {
                    StopWatch stopWatch = new StopWatch();
                    stopWatch.start();
                    filterChain.doFilter(servletRequest, servletResponse);
                    stopWatch.stop();
                    Long ttr = stopWatch.getTotalTimeMillis();
                    requestDetailService.insertRequestDetail(RequestDetailCreator.createRequestDetail(clientId, httpServletRequest.getMethod(), System.currentTimeMillis(), ttr, httpServletRequest.getRequestURI()));
                }
            }
        } catch (Exception e) {
            LOGGER.error("Exception in request filter", e);
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .message(e.getMessage())
                    .timestamp(System.currentTimeMillis())
                    .clientId(clientId)
                    .build();
            ((HttpServletResponse) servletResponse).setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            servletResponse.getWriter().println(mapper.writeValueAsString(errorResponse));
            servletResponse.setContentType("application/json");
        }
    }
}
