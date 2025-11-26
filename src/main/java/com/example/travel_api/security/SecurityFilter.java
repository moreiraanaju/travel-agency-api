package com.example.travel_api.security;

import com.example.travel_api.repositories.UserRepository;
import com.example.travel_api.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        
        // DEBUG 1: O token chegou?
        System.out.println("DEBUG - Token recebido: " + token);

        if(token != null){
            var login = tokenService.validateToken(token);
            // DEBUG 2: O token é valido e tem um login dentro?
            System.out.println("DEBUG - Login extraído: " + login);

            if(login != null && !login.isEmpty()){
                UserDetails user = userRepository.findByUsername(login);
                // DEBUG 3: O usuário existe no banco? Quais permissões ele tem?
                System.out.println("DEBUG - Usuário encontrado: " + (user != null ? user.getUsername() : "NÃO ACHOU"));
                if (user != null) {
                    System.out.println("DEBUG - Permissões do usuário: " + user.getAuthorities());
                }

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
    }
    filterChain.doFilter(request, response);
}

    private String recoverToken(HttpServletRequest request){
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        // O token vem assim: "Bearer eyJhbGciOiJIUzI1Ni..."
        // A gente remove a palavra "Bearer " para pegar só o código
        return authHeader.replace("Bearer ", "");
    }
}