package br.com.fiap.mypets.repository;

import br.com.fiap.mypets.model.entity.PetEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<PetEntity, String> {
}
