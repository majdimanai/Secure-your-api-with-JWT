package com.majdi.security.auth;

import com.majdi.security.config.JwtService;
import com.majdi.security.user.Role;
import com.majdi.security.user.User;
import com.majdi.security.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
private final UserRepository userRepository;
private final PasswordEncoder passwordEncoder;
private final JwtService jwtService;
private final AuthenticationManager authenticationManager;
    public AuthenticationResponse registre(RegistreRequest registreRequest) {
        var user= User.builder()
                .name(registreRequest.getName())
                .email(registreRequest.getEmail())
                .pass(passwordEncoder.encode(registreRequest.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
    var jwt=jwtService.generateToken(user);
        return  AuthenticationResponse.builder().token(jwt).build();
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );
        var user=userRepository.findByEmail(authenticationRequest.getEmail()).orElseThrow();
        var jwt=jwtService.generateToken(user);
        return  AuthenticationResponse.builder().token(jwt).build();
    }



}
