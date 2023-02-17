package br.com.fiap.mypets.services;

import br.com.fiap.mypets.entity.OwnerEntity;
import br.com.fiap.mypets.repository.OwnerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OwnerService {

    Logger LOG = LoggerFactory.getLogger(OwnerService.class);

    @Autowired
    private OwnerRepository repository;

    public OwnerEntity save(OwnerEntity owner){
        owner.setId(UUID.randomUUID().toString());
        LOG.info("Salvando owner: "+owner);
        return repository.save(owner);

    }

    public void delete(String id){
        LOG.info("Deletando owner: " + id);
        repository.deleteById(id);
    }

    public OwnerEntity find(String id){
        LOG.info("Buscar owner: " + id);
        return repository.findById(id).get();
    }
}
