package com.securepass.apisecurepass.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

public record UserDto(
        int matricula, // Número de identificação do usuário
        String nome, // Nome do usuário
        String setor, // Área de trabalho ou setor do usuário

        @NotBlank @Email(message = "O email deve estar em um formato válido") String email, // Email do usuário (validado quanto ao formato)

//      @NotBlank String senha, deve ser analisado - Provavelmente um campo para senha (comentado para análise)

        @DateTimeFormat(pattern = "yyyy-MM-dd") Date nascimento , // Data de nascimento do usuário

        String funcao, // Função ou cargo do usuário
        int sessao, // Número de sessão
        MultipartFile face, // Arquivo de imagem para o rosto do usuário
        UUID typeUser // Tipo de usuário (representado por um UUID)

) {
}
