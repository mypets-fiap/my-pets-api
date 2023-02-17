package br.com.fiap.mypets.entity;

import jakarta.persistence.*;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

@Entity
@Table(name = "tb_pet")
public class PetEntity implements Serializable {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    private String nome;
    private String raca;

    @ManyToOne
    @JoinColumn(name="owner_id", referencedColumnName="id", nullable=false)
    private OwnerEntity owner;

    public PetEntity() {
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

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public OwnerEntity getOwner() {
        return owner;
    }

    public void setOwner(OwnerEntity owner){
        this.owner = owner;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("id", id)
                .append("nome", nome)
                .append("raca", raca)
                .toString();
    }
}


