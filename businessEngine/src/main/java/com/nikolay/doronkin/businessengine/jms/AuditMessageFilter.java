package com.nikolay.doronkin.businessengine.jms;

import com.nikolay.doronkin.businessengine.jwt.JwtUser;
import com.nikolay.doronkin.starter.AuditMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class AuditMessageFilter extends GenericFilterBean {

    private final JmsProducer jmsProducer;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        filterChain.doFilter(servletRequest, servletResponse);
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
            jmsProducer.sendMessage( AuditMessage.builder()
                    .endpointName(ServletUriComponentsBuilder.fromCurrentRequest().toUriString())
                    .userEmail(jwtUser.getUser().getEmail())
                    .statusCode(((HttpServletResponse) servletResponse).getStatus())
                    .timestamp(LocalDateTime.now())
                    .build());
        }
    }
}
