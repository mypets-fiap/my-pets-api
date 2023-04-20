package br.com.fiap.mypets.services;

import br.com.fiap.mypets.domain.exception.UnauthorizedException;
import br.com.fiap.mypets.domain.interfaces.PetService;
import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.UserResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;
import br.com.fiap.mypets.domain.interfaces.repository.PetRepository;
import br.com.fiap.mypets.domain.interfaces.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PetServiceImpl implements PetService {
    Logger LOG = LoggerFactory.getLogger(PetServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PetRepository repository;

    public PetResponse save(User user, PetEntity pet){
        pet.setUser(user);
        pet.setId(UUID.randomUUID().toString());

        repository.save(pet);
        UserResponse userResponse = new UserResponse(pet.getUser());
        return new PetResponse(pet, userResponse);
    }

    public PetResponse update(User user, PetEntity pet){
        if(!user.getId().equals(pet.getUser().getId())){
            throw new UnauthorizedException("Ops, você não possui permissão para alterar este pet!");
        }

        pet.setUser(user);
        repository.save(pet);
        UserResponse userResponse = new UserResponse(pet.getUser());
        return new PetResponse(pet, userResponse);
    }

    public void delete(String id, User user){
        PetResponse pet = this.find(id, user);
        LOG.info("Excluindo petId [{}] do userId [{}]", pet.getId(), user.getId());
        repository.deleteById(pet.getId());
        LOG.info("PetId [{}] excluido com sucesso.", pet.getId());
    }

    public PetResponse find(String id,  User user){
        LOG.info("Buscando petId [{}] do userId [{}]", id, user.getId());
        Optional<PetEntity> petEntity = repository.findById(id);
        if(petEntity.isPresent()){
            PetEntity pet = petEntity.get();
            if(user.getId().equals(pet.getUser().getId())){
                LOG.info("Pet encontrado do userId [{}]: [{}]", user.getId(), pet);
                UserResponse userResponse = new UserResponse(pet.getUser());
                return new PetResponse(pet, userResponse);
            } else {
                throw new UnauthorizedException("Ops, você não possui permissão para buscar este pet!");
            }
        }
        LOG.info("Nenhum pet encontrado para o id {}", id);
        return null;
    }

    @Override
    public List<PetResponse> findByUser(User user) {
        LOG.info("Buscando todos os pets do userId [{}]", user.getId());
        List<PetEntity> pets = repository.findByUser(user);
        LOG.info("Pets encontrados do userId [{}]: [{}]", user.getId(), pets);
        return pets.stream()
                .map(pet -> new PetResponse(pet, new UserResponse(pet.getUser())))
                .collect(Collectors.toList());
    }

}