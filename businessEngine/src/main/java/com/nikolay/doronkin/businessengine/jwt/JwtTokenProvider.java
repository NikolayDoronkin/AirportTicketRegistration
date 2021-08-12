package com.nikolay.doronkin.businessengine.jwt;

import com.nikolay.doronkin.businessengine.enumeration.UserRole;
import com.nikolay.doronkin.businessengine.enumeration.UserStatus;
import com.nikolay.doronkin.businessengine.exception.ExceptionMessages;
import com.nikolay.doronkin.businessengine.model.User;
import com.nikolay.doronkin.businessengine.repository.UserRepository;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${jwt.token_secret}")
    private String secret;

    @Value("${jwt.token_expired}")
    private Long validityInMilliseconds;

    private final UserRepository userRepository;

    private static final String ROLES = "roles";

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public String createToken(String userName, List<UserRole> roles) {
        Claims claims = Jwts.claims().setSubject(userName);
        claims.put(ROLES, getListOfRolesName(roles));
        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    private List<String> getListOfRolesName(List<UserRole> roles) {
        return roles.stream().map(Enum::name).collect(Collectors.toList());
    }

    @Bean
    public UserDetailsService userDetailsServiceBean() {
        return userName -> {
            User expectedUser = userRepository.findUserByUserName(userName);
            if (expectedUser == null) {
                throw new UsernameNotFoundException(ExceptionMessages.USER_NOT_FOUND_WITH_NAME + userName);
            }
            return JwtUser.builder()
                    .user(expectedUser)
                    .enable(
                            expectedUser.getStatus().equals(UserStatus.ACTIVE)
                    )
                    .authorities(
                            List.of(
                                    new SimpleGrantedAuthority(expectedUser.getRoles().name())
                            )
                    )
                    .build();
        };
    }
}
