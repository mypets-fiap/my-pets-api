package br.com.fiap.mypets.services;

import br.com.fiap.mypets.model.entity.PetEntity;
import br.com.fiap.mypets.model.entity.User;
import br.com.fiap.mypets.repository.PetRepository;
import br.com.fiap.mypets.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class PetService {
    Logger LOG = LoggerFactory.getLogger(PetService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository repository;

    public PetEntity save(String email, PetEntity pet){

        User user = userRepository.findByEmail(email).orElseThrow();
        pet.setUser(user);

        if(pet.getId() != null && !pet.getId().isEmpty()){
            LOG.info("Alterando pet: "+pet);
        }else{
            pet.setId(UUID.randomUUID().toString());
            LOG.info("Salvando pet: "+pet);
        }
        return repository.save(pet);
    }

    public void delete(String id){
        LOG.info("Deletando pet: " + id);
        repository.deleteById(id);
    }

    public PetEntity find(String id){
        LOG.info("Buscar pet: " + id);
        return repository.findById(id).get();
    }

}