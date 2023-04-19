package br.com.fiap.mypets.domain.interfaces.repository;

import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, String> {

    List<PetEntity> findByUser(User user);
}
