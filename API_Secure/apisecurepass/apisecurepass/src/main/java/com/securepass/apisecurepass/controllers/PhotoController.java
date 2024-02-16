package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.azure.Blob;
import com.securepass.apisecurepass.dtos.PhotoDto;
import com.securepass.apisecurepass.models.UserModel;
import com.securepass.apisecurepass.repositories.PhotoRepository;

import com.securepass.apisecurepass.repositories.UserRepository;
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
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping(value = "/photo", produces = {"application/json"})
public class PhotoController {
    @Autowired
    PhotoRepository photoRepository;
    @Autowired
    UserRepository userRepository;

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

                    // Retorna a resposta como JSON com o status HTTP  202 OK
                    //return ResponseEntity.ok().body(responseBody);

                    // Supondo que o responseBody contenha o nome do arquivo com a extensão
                    String fileNameWithExtension = responseBody;

                    // Encontra o índice do último ponto na string
                    int lastDotIndex = fileNameWithExtension.lastIndexOf('.');

                    // Se houver um ponto na string (ou seja, a extensão existe), remove a extensão
                    if (lastDotIndex >=  0) {
                        // Extrai o nome do arquivo sem a extensão
                        String fileNameWithoutExtension = fileNameWithExtension.substring(0, lastDotIndex);
                        String TextFinal = fileNameWithoutExtension;
                        TextFinal = TextFinal
                                .replace("\"", "")
                                .replace(":", "")
                                .replace("{", "")
                                .replace("}", "");
                        // Agora fileNameWithoutExtension contém o nome do arquivo sem a extensão .png
                        System.out.println("Nome do arquivo sem extensão: " + TextFinal);

                        // Aqui você deve implementar a lógica para buscar o usuário pelo nome do arquivo
                        // Você pode usar o fileNameWithoutExtension como o ID do usuário
                        // Por exemplo:
                        UUID TextForFind = UUID.fromString(TextFinal);
                        Optional<UserModel> user = userRepository.findById(TextForFind);
                        if (user.isPresent()){
                            UserModel userLog= user.get();
                            System.out.println(userLog.getId());

                            return ResponseEntity.ok().body(userLog);
                        }
                        // Se o usuário for encontrado, retorne suas informações
                        return ResponseEntity.ok().body(user);

                    }

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


