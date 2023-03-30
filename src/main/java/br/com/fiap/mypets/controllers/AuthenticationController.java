package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.exception.BadRequestException;
import br.com.fiap.mypets.exception.UnauthorizedException;
import br.com.fiap.mypets.model.AuthenticationRequest;
import br.com.fiap.mypets.model.RegisterRequest;
import br.com.fiap.mypets.model.ResponseMyPetsEntity;
import br.com.fiap.mypets.services.AuthenticationService;
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
            log.info("Cadastrando nova credencial: [{}]", request);
            return ResponseEntity.ok(new ResponseMyPetsEntity(service.register(request)));
        }catch (BadRequestException ex){
            throw ex;
        }
        catch (Exception ex){
            log.error("Erro inesperado ao registrar um usuario", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }
    }

    @PostMapping("/authenticate")
    public ResponseEntity authenticate(@RequestBody AuthenticationRequest request) throws UnauthorizedException {
        try {
            log.info("Efetuando login do usuário [{}]", request);
            return ResponseEntity.ok(new ResponseMyPetsEntity(service.authenticate(request)));
        }catch (BadCredentialsException ex) {
            throw new UnauthorizedException("Usuário ou senha inválido.");
        }
        catch (Exception ex){
            log.error("Erro inesperado ao autenticar um usuario", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }
    }
}
