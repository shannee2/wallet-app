package com.walletapp.config;

import com.walletapp.exceptions.UserNotFoundException;
import com.walletapp.model.user.User;
import com.walletapp.model.user.UserPrincipal;
import com.walletapp.service.JWTService;
import com.walletapp.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
//    ApplicationContext context;

    ApplicationContext context;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userId = null;

        if(authHeader!=null &&authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            userId = jwtService.extractUserId(token);
        }

        if(userId != null && SecurityContextHolder.getContext().getAuthentication() == null){
            User user;
            try {
                user = context.getBean(UserService.class).getUserById(userId);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            UserDetails userDetails = new UserPrincipal(user);

            if(jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
