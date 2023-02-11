package br.com.fiap.mypets.model.entity;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Pet {

    @Id
    @Column(name = "id", nullable = false)
    private String id = UUID.randomUUID().toString();

    private String nome;
    private String raca;

    public Pet() {
    }

    public String getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }
}


