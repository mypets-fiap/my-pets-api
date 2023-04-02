package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.domain.model.PetResponse;
import br.com.fiap.mypets.domain.model.entity.PetEntity;
import br.com.fiap.mypets.domain.model.ResponseMyPetsEntity;
import br.com.fiap.mypets.services.PetService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/pet")
public class PetController {

    Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService service;

    @GetMapping("/{id}")
    public ResponseEntity selectPet(@PathVariable String id){
        try {
            PetResponse pet = service.find(id);
            return ResponseEntity.ok(new ResponseMyPetsEntity(pet));
        }catch (Exception ex){
            LOG.error("Erro inesperado ao consultar um pet", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }

    }

    @PostMapping
    public ResponseEntity savePet(@RequestHeader (name="Authorization") String token, @RequestBody PetEntity pet){
        try {
            DecodedJWT decodedJWT = JWT.decode(token.substring("Bearer ".length()));
            String email = decodedJWT.getClaim("sub").asString();
            PetResponse petResponse = service.save(email, pet);
            return ResponseEntity.created(new URI("/pet/"+ petResponse.getId()))
                                    .body(new ResponseMyPetsEntity(petResponse));
        }catch (Exception ex){
            LOG.error("Erro inesperado ao cadastrar um pet", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity alterPet(@RequestHeader (name="Authorization") String token,
                                   @RequestBody @NotNull PetEntity pet,
                                   @PathVariable String id){

        try {
            DecodedJWT decodedJWT = JWT.decode(token.substring("Bearer ".length()));
            String email = decodedJWT.getClaim("sub").asString();
            pet.setId(id);
            PetResponse petResponse = service.save(email, pet);
            return ResponseEntity.ok(new ResponseMyPetsEntity(petResponse));
        }catch (Exception ex){
            LOG.error("Erro inesperado ao alterar um pet", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity removePet(@PathVariable String id){
        try {
            service.delete(id);
            return  ResponseEntity.ok(new ResponseMyPetsEntity("Pet excluído com sucesso."));
        }catch (Exception ex){
            LOG.error("Erro inesperado ao deletar um pet", ex);
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }
    }
}
