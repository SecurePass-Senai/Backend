package com.securepass.apisecurepass.dtos;

import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.UUID;

public record LoginDto(
        MultipartFile image


) {
}
