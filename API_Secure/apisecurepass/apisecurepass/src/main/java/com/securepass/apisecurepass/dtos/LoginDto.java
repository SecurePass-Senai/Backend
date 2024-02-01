package com.securepass.apisecurepass.dtos;

import java.util.Date;
import java.util.UUID;

public record LoginDto(
        UUID UserID,

        Date login_time
) {
}
