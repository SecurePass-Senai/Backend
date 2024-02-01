package com.securepass.apisecurepass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_usuariomaquina")
public class UserMachineModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "id_usuario" ,referencedColumnName = "id")
    private UserModel UserID;

    @ManyToOne
    @JoinColumn(name = "id_maquina" ,referencedColumnName = "id")
    private MachineModel MachineID;

}