package com.majdi.security.auth;

import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;

@PostMapping("/registre")
    public ResponseEntity<AuthenticationResponse> registre(@RequestBody RegistreRequest request){
return ResponseEntity.ok(authenticationService.registre(request));
}


    @PostMapping("/authenticaate")
    public ResponseEntity<AuthenticationResponse> registre(@RequestBody AuthenticationRequest  request){
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }


}
