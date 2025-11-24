package com.example.travel_api.controllers;

import com.example.travel_api.User;
import com.example.travel_api.dtos.AuthenticationDTO;
import com.example.travel_api.dtos.LoginResponseDTO;
import com.example.travel_api.dtos.RegisterDTO;
import com.example.travel_api.services.TokenService;
import com.example.travel_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository repository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody AuthenticationDTO data){
        // Cria um token simples com os dados (ainda não valida nada)
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.username(), data.password());
        
        // O authenticationManager que vai chamar o AuthorizationService e verificar no banco se a senha bate
        var auth = this.authenticationManager.authenticate(usernamePassword);

        // Pegamos o usuário autenticado (getPrincipal) e geramos o token
        var token = tokenService.generateToken((User) auth.getPrincipal());

        // Se chegar aqui, deu certo! (Se a senha estiver errada, o Spring lança erro automaticamente)
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody RegisterDTO data){
        // Verifica se já existe um usuário com esse login
        if(this.repository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();

        // 1. Criptografa a senha recebida
        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        
        // 2. Cria o usuário novo com a senha criptografada
        User newUser = new User(data.username(), encryptedPassword, data.role());

        // 3. Salva no banco
        this.repository.save(newUser);

        return ResponseEntity.ok().build();
    }
}