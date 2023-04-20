package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.domain.exception.BadRequestException;
import br.com.fiap.mypets.domain.exception.UnauthorizedException;
import br.com.fiap.mypets.domain.interfaces.AuthenticationService;
import br.com.fiap.mypets.domain.model.AuthenticationRequest;
import br.com.fiap.mypets.domain.model.RegisterRequest;
import br.com.fiap.mypets.domain.model.ResponseMyPetsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    public ResponseEntity register(@RequestBody RegisterRequest request) throws Exception {

        try {
            log.info("Cadastrando novo usuário: [{}]", request);
            return ResponseEntity.ok(service.register(request));
        }catch (BadRequestException ex){
            throw ex;
        }
        catch (Exception ex){
            log.error("Erro inesperado ao cadastrar um usuario", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws UnauthorizedException {
        try {
            log.info("Efetuando login do usuário [{}]", request);
            return ResponseEntity.ok(service.authenticate(request));
        }catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Usuário ou senha inválido.");
        }
        catch (Exception ex){
            log.error("Erro inesperado ao autenticar um usuario", ex);
            return ResponseEntity.internalServerError().body((ex.getMessage()));
        }
    }
}
