package com.ibm.mateusmelo.security.jwt.filter;

import com.ibm.mateusmelo.security.jwt.JwtService;
import com.ibm.mateusmelo.service.impl.UserServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    private UserServiceImpl userService;

    public JwtAuthFilter(JwtService jwtService, UserServiceImpl userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String auth = request.getHeader("Authorization");
            if(auth != null && auth.startsWith("Bearer ")) {
                String token = auth.split(" ")[1];
                if(jwtService.isValidToken(token)) {
                    String username = jwtService.getLoginUsername(token);
                    UserDetails user = userService.loadUserByUsername(username);
                    UsernamePasswordAuthenticationToken userAuth = new UsernamePasswordAuthenticationToken(
                            user,
                            null,
                            user.getAuthorities()
                    );

                    userAuth.setDetails(user);
                    SecurityContextHolder.getContext().setAuthentication(userAuth);
                };
            }

        filterChain.doFilter(request, response);
    }
}
