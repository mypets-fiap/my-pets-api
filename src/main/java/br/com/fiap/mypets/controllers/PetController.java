package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.model.entity.PetEntity;
import br.com.fiap.mypets.model.ResponseMyPetsEntity;
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
@RequestMapping("/pet")
public class PetController {

    Logger LOG = LoggerFactory.getLogger(PetController.class);

    @Autowired
    private PetService service;

    @GetMapping("/{id}")
    public ResponseEntity selectPet(@RequestHeader (name="Authorization") String token, @PathVariable String id){
        DecodedJWT decodedJWT = JWT.decode(token.substring("Bearer ".length()));
        
        return ResponseEntity.ok(new PetEntity());
    }

    @PostMapping
    public ResponseEntity savePet(@RequestBody PetEntity pet){
        try {
            LOG.info("Pet recebido: "+pet);
            pet = service.save(pet);
            return ResponseEntity.created(new URI("/pet/"+ pet.getId()))
                                    .body(new ResponseMyPetsEntity(pet));
        }catch (Exception ex){
            return ResponseEntity.internalServerError().body(new ResponseMyPetsEntity(ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity alterPet(@RequestBody @NotNull PetEntity pet, @PathVariable String id){
        pet.setId(id);
        pet = service.save(pet);
        return ResponseEntity.ok(new ResponseMyPetsEntity(pet));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removePet(@PathVariable String id){
        service.delete(id);
        return  ResponseEntity.ok(new ResponseMyPetsEntity("Pet excluído com sucesso."));
    }
}
