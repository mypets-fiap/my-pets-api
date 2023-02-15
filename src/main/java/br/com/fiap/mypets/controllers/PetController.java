package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.entity.PetEntity;
import br.com.fiap.mypets.services.PetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pet")
public class PetController {

    Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService service;

    @PostMapping
    public PetEntity savePet(@RequestBody PetEntity pet){
        LOG.info("Pet recebido: "+pet);
        return service.save(pet);
    }
}
