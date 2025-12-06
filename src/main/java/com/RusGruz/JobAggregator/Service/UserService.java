package com.RusGruz.JobAggregator.Service;

import com.RusGruz.JobAggregator.Models.Users;
import com.RusGruz.JobAggregator.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepo repo;
@Autowired
    private JWTService jwtService;
@Autowired
private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);
    public Users register(Users user){
        user.setPassword(encoder.encode(user.getPassword()));
return repo.save(user);
    }

    public String verify(Users user) {
      
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword())
            );

            if (auth.isAuthenticated()) {
                return jwtService.generateToken(user.getUsername());
            }
return "fail";


    }
}
