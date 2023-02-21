package br.com.fiap.mypets.security.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/internal-controller")
public class InternalController {

    @GetMapping
    public String internalEndpoint(){
        return "Olá, você acessou um endpoint interno!";
    }
}
