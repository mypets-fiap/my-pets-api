package br.com.fiap.mypets.controllers;

import br.com.fiap.mypets.entity.OwnerEntity;
import br.com.fiap.mypets.entity.ResponseMyPetsEntity;
import br.com.fiap.mypets.services.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/owner")
public class OwnerController {

    Logger LOG = LoggerFactory.getLogger(OwnerController.class);

    @Autowired
    private OwnerService service;

    @GetMapping("/{id}")
    public ResponseEntity selectOwner(@PathVariable String id){
        return ResponseEntity.ok(new ResponseMyPetsEntity(service.find(id)));
    }

    @PostMapping
    public ResponseEntity saveOwner(@RequestBody OwnerEntity owner) throws URISyntaxException {
        LOG.info("Owner recebido: " + owner);
        owner = service.save(owner);
        return ResponseEntity.created(new URI("/Owner/"+ owner.getId()))
                             .body(new ResponseMyPetsEntity(owner));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removOwner(@PathVariable String id){
        service.delete(id);
        return  ResponseEntity.ok(new ResponseMyPetsEntity("Owner e Pets excluído com sucesso"));
    }

}
