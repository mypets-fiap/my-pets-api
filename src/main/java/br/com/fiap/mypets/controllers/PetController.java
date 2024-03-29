package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.domain.interfaces.AuthenticationService;
import br.com.fiap.mypets.domain.interfaces.PetService;
import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.entity.User;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService service;

    @Autowired
    private AuthenticationService authenticationService;

    @GetMapping("/{id}")
    public ResponseEntity getPet(@PathVariable String id, @RequestHeader String authorization){
        try {
            User user = authenticationService.extractUser(authorization);
            PetResponse pet = service.find(id, user);
            if(pet != null)
                return ResponseEntity.ok(pet);
            return ResponseEntity.noContent().build();
        }catch (Exception ex){
            LOG.error("Ocorreu um erro ao buscar o pet", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity getAllPets(@RequestHeader String authorization){
        try {
            User user = authenticationService.extractUser(authorization);
            List<PetResponse> pets = service.findByUser(user);
            if(pets != null && !pets.isEmpty()) {
                return ResponseEntity.ok(pets);
            }
            return ResponseEntity.noContent().build();
        }catch (Exception ex){
            LOG.error("Erro inesperado ao consultar todos os pets", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity savePet(@RequestHeader String authorization, @RequestBody PetEntity pet){
        try {
            User user = authenticationService.extractUser(authorization);
            PetResponse petResponse = service.save(user, pet);
            return ResponseEntity.created(new URI("/pet/"+ petResponse.getId()))
                                    .body(petResponse);
        }catch (Exception ex){
            LOG.error("Erro inesperado ao cadastrar um pet", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity alterPet(@RequestHeader String authorization,
                                   @RequestBody @NotNull PetEntity pet,
                                   @PathVariable String id){

        try {
            User user = authenticationService.extractUser(authorization);
            pet.setId(id);
            pet.setUser(user);
            PetResponse petResponse = service.update(user, pet);
            return ResponseEntity.ok(petResponse);
        }catch (Exception ex){
            LOG.error("Erro inesperado ao alterar um pet", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity removePet(@RequestHeader String authorization, @PathVariable String id){
        try {
            User user = authenticationService.extractUser(authorization);
            service.delete(id, user);
            return  ResponseEntity.ok("Pet excluído com sucesso.");
        }catch (Exception ex){
            LOG.error("Erro inesperado ao deletar um pet", ex);
            return ResponseEntity.internalServerError().body(ex.getMessage());
        }
    }
}
