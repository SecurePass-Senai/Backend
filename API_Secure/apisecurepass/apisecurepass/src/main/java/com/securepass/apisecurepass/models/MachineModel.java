package com.securepass.apisecurepass.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_maquina")
public class MachineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    private String nome;

    private int codigomaquina;

    @ManyToMany
    @JsonIgnore // Ignora a serialização deste campo ao converter para JSON
    @JoinTable(
            name = "tb_usuariomaquina",
            joinColumns = @JoinColumn(name = "id_maquina"), // Chave estrangeira referente a Machine
            inverseJoinColumns = @JoinColumn(name = "id_usuario")) // Chave estrangeira referente a User
    Set<UserModel> users;


}