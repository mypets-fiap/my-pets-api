package br.com.fiap.mypets.domain.interfaces;

import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;

import java.util.List;

public interface PetService {

    PetResponse save(String email, PetEntity pet);
    PetResponse find(String id, User user);

    List<PetResponse> findByUser(User user);
    void delete(String id, User user);

}
