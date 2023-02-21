package br.com.fiap.mypets.security.controllers;

import br.com.fiap.mypets.security.controllers.model.AuthenticationRequest;
import br.com.fiap.mypets.security.controllers.model.AuthenticationResponse;
import br.com.fiap.mypets.security.controllers.model.RegisterRequest;
import br.com.fiap.mypets.security.services.AuthenticationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

    Logger log = LoggerFactory.getLogger(AuthenticationController.class);

    @Autowired
    private AuthenticationService service;

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        log.info("Cadastrando nova credencial: [{}]", request);
        return ResponseEntity.ok(service.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        log.info("Efetuando login do usuário [{}]", request);
        return ResponseEntity.ok(service.authenticate(request));
    }
}
