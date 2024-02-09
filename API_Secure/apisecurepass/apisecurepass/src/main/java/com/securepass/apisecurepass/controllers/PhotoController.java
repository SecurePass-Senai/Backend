package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.azure.Blob;
import com.securepass.apisecurepass.dtos.PhotoDto;
import com.securepass.apisecurepass.repositories.PhotoRepository;

import com.securepass.apisecurepass.services.FileUploadService;
import jakarta.validation.Valid;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


import java.io.File;
import java.io.IOException;


@RestController
@RequestMapping(value = "/photo", produces = {"application/json"})
public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;

    // Define o endpoint que aceita requisições POST com multipart form data
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> uploadPhoto(@ModelAttribute @Valid PhotoDto photoDto) throws IOException {

        // Inicializa o cliente HTTP para realizar a requisição externa
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // Define o endpoint para a requisição POST
            HttpPost httpPost = new HttpPost("http://74.235.106.124:5000/login");

            // Extrai o arquivo do DTO
            MultipartFile file = photoDto.image();

            // Constrói o corpo da requisição com o arquivo
            HttpEntity entity = MultipartEntityBuilder.create()
                    .addBinaryBody("file", file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM, file.getOriginalFilename())
                    .build();

            // Define o corpo da requisição na requisição POST
            httpPost.setEntity(entity);

            // Executa a requisição e captura a resposta
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                // Imprime o status da resposta do servidor
                System.out.println("Resposta do servidor: " + response.getStatusLine());

                // Captura o corpo da resposta
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    // Converte o corpo da resposta para string
                    String responseBody = EntityUtils.toString(responseEntity);
                    System.out.println("Corpo da resposta: " + responseBody);

                    // Retorna a resposta como JSON com o status HTTP  200 OK
                    return ResponseEntity.ok().body(responseBody);
                }
            } finally {
                // Fecha a resposta para liberar recursos
                response.close();
            }
        } finally {
            // Fecha o cliente HTTP para liberar recursos
            httpClient.close();
        }

        // Caso nenhum corpo de resposta seja capturado, retorna uma resposta vazia com status HTTP  200 OK
        return ResponseEntity.ok().build();
    }


}


