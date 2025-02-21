package com.walletapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walletapp.dto.general.ErrResponse;
import com.walletapp.exceptions.users.UserNotFoundException;
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
    ApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        String token = null;
        Long userIdFromToken = null;

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            userIdFromToken = jwtService.extractUserId(token);
        }

        String path = request.getRequestURI();
        String[] pathParts = path.split("/");
        Long userIdFromUrl = null;

        if (pathParts.length > 2 && pathParts[1].equals("users")) {
            if (pathParts[2].equals("login")) {
                filterChain.doFilter(request, response);
                return;
            }

            try {
                userIdFromUrl = Long.parseLong(pathParts[2]);
            } catch (NumberFormatException e) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().write("Invalid User ID format in the URL");
                return;
            }

            userIdFromToken = jwtService.extractUserId(token);

            if (userIdFromToken != null && !userIdFromToken.equals(userIdFromUrl)) {
                ErrResponse errorResponse = new ErrResponse(HttpServletResponse.SC_FORBIDDEN, "Unauthorized: User ID in path does not match User ID in token");
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                response.setContentType("application/json");
                response.getWriter().write(objectMapper.writeValueAsString(errorResponse));
                return;
            }

        }

        if (userIdFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User user;
            try {
                user = context.getBean(UserService.class).getUserById(userIdFromToken);
            } catch (UserNotFoundException e) {
                throw new RuntimeException(e);
            }
            UserDetails userDetails = new UserPrincipal(user);

            if (jwtService.validateToken(token, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);

    }
}
