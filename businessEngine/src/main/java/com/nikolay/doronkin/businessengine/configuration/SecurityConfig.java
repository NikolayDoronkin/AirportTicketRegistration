package com.nikolay.doronkin.businessengine.configuration;

import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.jms.AuditMessageFilter;
import com.nikolay.doronkin.businessengine.jms.JmsProducer;
import com.nikolay.doronkin.businessengine.jwt.JwtConfigurer;
import com.nikolay.doronkin.businessengine.jwt.JwtTokenFilter;
import com.nikolay.doronkin.businessengine.jwt.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtUtil jwtUtil;
    private final JmsProducer jmsProducer;

    private static final String USER_ENDPOINT = "/api/auth/user";
    private static final String FLIGHT_ENDPOINT = "/api/flight/**";
    private static final String TICKET_ENDPOINT = "/api/ticket/**";
    private static final String LOGIN_ENDPOINT = "/api/auth/login";
    private static final String AIRPORT_ENDPOINT = "/api/airport/**";
    private static final String SIGNING_ENDPOINT = "/api/auth/sign-up";

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public JwtTokenFilter jwtAuthenticationFilter() {
        return new JwtTokenFilter(jwtUtil);
    }

    @Bean
    public FilterRegistrationBean<AuditMessageFilter> auditMessageFilter() {
        FilterRegistrationBean<AuditMessageFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new AuditMessageFilter(jmsProducer));
        registrationBean.addUrlPatterns(FLIGHT_ENDPOINT, TICKET_ENDPOINT, USER_ENDPOINT);
        return registrationBean;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                    .antMatchers(LOGIN_ENDPOINT)
                        .permitAll()
                    .antMatchers(SIGNING_ENDPOINT)
                        .permitAll()
                    .antMatchers(USER_ENDPOINT)
                        .hasAnyRole(UserRole.ROLE_ADMIN.name().substring(5), UserRole.ROLE_USER.name().substring(5))
                    .antMatchers(AIRPORT_ENDPOINT)
                        .hasAnyRole(UserRole.ROLE_ADMIN.name().substring(5), UserRole.ROLE_USER.name().substring(5))
                    .antMatchers(FLIGHT_ENDPOINT)
                        .hasAnyRole(UserRole.ROLE_ADMIN.name().substring(5), UserRole.ROLE_USER.name().substring(5))
                    .antMatchers(TICKET_ENDPOINT)
                        .hasAnyRole(UserRole.ROLE_ADMIN.name().substring(5), UserRole.ROLE_USER.name().substring(5))
                .anyRequest()
                    .permitAll()
                .and()
                .apply(new JwtConfigurer(jwtUtil));
        http.addFilterAfter(auditMessageFilter().getFilter(), UsernamePasswordAuthenticationFilter.class);
    }
}
