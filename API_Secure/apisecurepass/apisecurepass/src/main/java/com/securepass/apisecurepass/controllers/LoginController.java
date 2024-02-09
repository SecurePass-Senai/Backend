package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.azure.Blob;
import com.securepass.apisecurepass.dtos.LoginDto;
import com.securepass.apisecurepass.models.LoginModel;
import com.securepass.apisecurepass.repositories.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping(value = "/login", produces = {"application/json"})
public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> loginComFoto(@ModelAttribute @Valid LoginDto loginDto){

        MultipartFile file = loginDto.image();

        String extensaoArquivo = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

        String nomeArquivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + "." + extensaoArquivo;

        var uploadBlob = Blob.UploadFileToBlob( file, nomeArquivo );

        return ResponseEntity.status(HttpStatus.OK).body( uploadBlob);
    }

}
