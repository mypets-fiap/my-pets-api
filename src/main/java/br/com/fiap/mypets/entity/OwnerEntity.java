package br.com.fiap.mypets.entity;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "tb_owner")
public class OwnerEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false, unique=true)
    private String id;

    private String nome;

    private int idade;

    @OneToMany(mappedBy="owner", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<PetEntity> pets;

    public OwnerEntity(){
        super();
    }

    public void setId(String id) {
        this.id = id;
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

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }

    @Override
    public String toString() {
        return "OwnerEntity{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", idade=" + idade +
                ", pets=" + pets +
                '}';
    }
}
