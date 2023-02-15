package br.com.fiap.mypets.services;

import br.com.fiap.mypets.controllers.PetController;
import br.com.fiap.mypets.entity.PetEntity;
import br.com.fiap.mypets.repository.PetRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetService {

    Logger LOG = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private PetRepository repository;

    public PetEntity save(PetEntity pet){
        pet.setId(UUID.randomUUID().toString());
        LOG.info("Salvando pet: "+pet);
        return repository.save(pet);
    }
}
