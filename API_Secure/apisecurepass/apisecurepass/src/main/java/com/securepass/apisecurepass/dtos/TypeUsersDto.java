package com.securepass.apisecurepass.dtos;

import jakarta.validation.constraints.NotBlank;

public record TypeUsersDto(
        @NotBlank String tipo

) {
}
