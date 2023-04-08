package br.com.fiap.mypets.domain.interfaces;

import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;

public interface PetService {

    PetResponse save(String email, PetEntity pet);
    PetResponse find(String id);
    void delete(String id);

}
