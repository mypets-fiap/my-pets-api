package br.com.fiap.mypets.repository;

import br.com.fiap.mypets.entity.OwnerEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends CrudRepository<OwnerEntity, String> {
}
