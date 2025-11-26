package com.example.travel_api;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import jakarta.persistence.*;
import java.util.Collection;
import java.util.List;


@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password; // Será guardada criptografada!
    
    private String role; // Ex: "ADMIN", "USER"

    public User() {}   // construtor vazio (JPA exige um constutor sem argumentos)

    public User(String username, String password, String role) {   // construtor com argumentos serve para facilitar a criação manual
        this.username = username;
        this.password = password;
        this.role = role;
    }

    //getter
    public String getRole() {
        return this.role;
    }

    //seters    
    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }


 @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role.equals("ADMIN")) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN"), new SimpleGrantedAuthority("ROLE_USER"));
        } else {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    }

    @Override
    public String getUsername() { return username; }
    
    @Override
    public String getPassword() { return password; }

    // Controles de conta (para simplificar, retornamos true)
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}
