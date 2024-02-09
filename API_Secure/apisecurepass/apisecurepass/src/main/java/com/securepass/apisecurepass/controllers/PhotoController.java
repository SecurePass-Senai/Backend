package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.azure.Blob;
import com.securepass.apisecurepass.dtos.PhotoDto;
import com.securepass.apisecurepass.repositories.PhotoRepository;

import com.securepass.apisecurepass.services.FileUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping(value = "/photo", produces = {"application/json"})
public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;


    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadPhoto(@ModelAttribute @Valid PhotoDto photoDto) {

        String imgUrl = "";

        try {

            MultipartFile file = photoDto.image();

            String extensaoArquivo = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.') + 1);

            String nomeArquivo = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyyHHmmss")) + "." + extensaoArquivo;

            var uploadBlob = Blob.UploadFileToBlob(file, nomeArquivo);


            // Construir o corpo da solicitação para o serviço Python
            String requestBody = nomeArquivo;

            // Criação de um cliente HTTP para enviar a solicitação para o serviço Python
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://74.235.106.124:5000/login")) // URL do serviço Python
                    .header("Content-Type", "multipart/form-data")

                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            // Envio da solicitação HTTP e recebimento da resposta
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println(response);
            String errorMessage = response.toString();
            String message = nomeArquivo;
            System.out.println("Erro da API Python: " + errorMessage);
            System.out.println("Erro Aquiii " + message);

            // Verificação do código de status da resposta e retorno apropriado
            if (response.statusCode() == HttpStatus.OK.value()) {
                return ResponseEntity.status(HttpStatus.OK).body(response.body());
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao processar a solicitação.");
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }

        return ResponseEntity.status(HttpStatus.OK).body(imgUrl);
    }


}


