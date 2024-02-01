package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.config.Blob;
import com.securepass.apisecurepass.dtos.PhotoDto;
import com.securepass.apisecurepass.repositories.PhotoRepository;
import com.securepass.apisecurepass.repositories.TypeUsersRepository;
import com.securepass.apisecurepass.repositories.UserRepository;
import com.securepass.apisecurepass.services.FileUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "/photo", produces = {"application/json"})
public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    private UserRepository userRepository;


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadPhoto(@ModelAttribute @Valid PhotoDto photoDto) {

        String imgUrl;

        try {
            imgUrl = FileUploadService.FazerUpload(photoDto.image());

            var upload = Blob.UploadFileToBlob( imgUrl );

        }catch (IOException e ){
            throw new RuntimeException(e);
        }

        return ResponseEntity.status(HttpStatus.OK).body(imgUrl);
    }


}


