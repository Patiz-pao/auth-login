package com.authentication.login.config;

import com.authentication.login.entity.SessionToken;
import com.authentication.login.entity.UserEntity;
import com.authentication.login.repository.SessionTokenRepo;
import com.authentication.login.repository.UserRepo;
import com.authentication.login.util.JwtUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.util.List;

import static com.authentication.login.util.Constants.USER_ROLE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;

    private final SessionTokenRepo sessionTokenRepo;
    private final UserRepo userRepo;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException, java.io.IOException {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = jwtUtil.validateToken(token);
            if (claims != null) {
                String username = claims.getSubject();
                String role = claims.get("role", String.class);
                if (role == null) {
                    role = USER_ROLE;
                }

                UserEntity user = userRepo.findByUsername(username);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                SessionToken sessionToken = sessionTokenRepo.findById(String.valueOf(user.getId())).orElse(null);
                if (sessionToken == null || !sessionToken.getToken().equals(token)) {
                    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                    return;
                }

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(username, null,
                                List.of(new SimpleGrantedAuthority("ROLE_" + role)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }
}

