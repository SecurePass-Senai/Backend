package com.securepass.apisecurepass.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id; // Identificador único para o usuário

    private int matricula; // Número de matrícula do usuário
    private String nome; // Nome do usuário
    private String setor; // Área de trabalho ou setor do usuário
    private String email; // Email do usuário

    @Temporal(TemporalType.DATE)
    private Date nascimento; // Data de nascimento do usuário

    private String funcao; // Função ou cargo do usuário
    private int sessao; // Número de sessão associado ao usuário
    private String face; // Representação da face do usuário (provavelmente um caminho ou referência para a imagem)

    // Relacionamento muitos para um (many-to-one) com o modelo de tipo de usuário
    @ManyToOne
    @JoinColumn(name = "id_tipousuario" , referencedColumnName = "id")
    private TypeUsersModel typeUser; // Tipo de usuário associado a este usuário

    @ManyToMany
    @JoinTable(
            name = "tb_usuariomaquina",
            joinColumns = @JoinColumn(name = "id_usuario"), // Chave estrangeira referente a Machine
            inverseJoinColumns = @JoinColumn(name = "id_maquina")) // Chave estrangeira referente a User
    Set<MachineModel> machines;
}
