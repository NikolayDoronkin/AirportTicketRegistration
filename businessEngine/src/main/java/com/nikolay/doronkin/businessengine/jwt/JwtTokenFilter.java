package com.nikolay.doronkin.businessengine.jwt;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class JwtTokenFilter extends GenericFilterBean {

    private final JwtUtil jwtUtil;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String token = jwtUtil.resolveToken((HttpServletRequest) servletRequest);
        log.info("TOKEN: {}", token);
        if (jwtUtil.validateToken(token)) {
            Authentication authentication = jwtUtil.getAuthentication(token);
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("AUTHENTICATED! {}", authentication);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
