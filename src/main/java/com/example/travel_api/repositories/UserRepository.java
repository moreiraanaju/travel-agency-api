
package com.example.travel_api.repositories;

import com.example.travel_api.User; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<User, Long> {
    
    UserDetails findByUsername(String username);
}
