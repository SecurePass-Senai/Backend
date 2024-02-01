package com.securepass.apisecurepass.dtos;

import org.springframework.web.multipart.MultipartFile;

public record PhotoDto(

        MultipartFile image
) {

}
