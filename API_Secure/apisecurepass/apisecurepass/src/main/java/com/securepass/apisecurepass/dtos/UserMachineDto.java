package com.securepass.apisecurepass.dtos;

import java.util.UUID;

public record UserMachineDto(
        UUID UserID,
        UUID MachineID
) {
}
