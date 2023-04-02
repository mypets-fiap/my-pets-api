package br.com.fiap.mypets.domain.model;

import br.com.fiap.mypets.domain.model.entity.PetEntity;

public class PetResponse {
    private String id;
    private String nome;
    private String raca;
    private UserResponse user;

    public PetResponse(String id, String nome, String raca, UserResponse user) {
        this.id = id;
        this.nome = nome;
        this.raca = raca;
        this.user = user;
    }

    public PetResponse(PetEntity pet, UserResponse user) {
        this.id = pet.getId();
        this.nome = pet.getNome();
        this.raca = pet.getRaca();
        this.user = user;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
