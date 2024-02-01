package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.config.Blob;
import com.securepass.apisecurepass.dtos.UserDto;
import com.securepass.apisecurepass.models.TypeUsersModel;
import com.securepass.apisecurepass.models.UserModel;
import com.securepass.apisecurepass.repositories.UserRepository;
import com.securepass.apisecurepass.services.FileUploadService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value = "/users", produces = {"application/json"})
public class UserController {

    // Injeção da dependência do repositório do usuário
    @Autowired
    UserRepository userRepository;

    @Autowired
    FileUploadService fileUploadService;

    // Endpoint para listar todos os usuários
    @GetMapping
    public ResponseEntity<List<UserModel>> listUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> searchUser(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> searchUser = userRepository.findById(id);

        if (searchUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(searchUser.get());
    }

    // Endpoint para cadastrar um novo usuário
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> userRegister(@ModelAttribute @Valid UserDto userDto) {
        // Verifica se o email já está cadastrado
        if (userRepository.findByEmail(userDto.email()) != null) {
            // Se o email já existe, retorna um erro
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Esse email já está cadastrado!");
        }

        // Converte o DTO do usuário para a entidade UserModel
        UserModel user = new UserModel();
        BeanUtils.copyProperties(userDto, user);

        String imgUrl;

        try {
            imgUrl = FileUploadService.FazerUpload(userDto.image());

            var upload = Blob.UploadFileToBlob(imgUrl);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Salva o novo usuário no banco de dados
        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<Object> editarUsuario(@PathVariable(value = "id") UUID id, @ModelAttribute @Valid UserDto userDto) {
        Optional<UserModel> searchUser = userRepository.findById(id);

        if (searchUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado");
        }

        UserModel user = searchUser.get();

        BeanUtils.copyProperties(userDto, user);
        String imgUrl;

        try {
            imgUrl = FileUploadService.FazerUpload(userDto.image());

            var upload = Blob.UploadFileToBlob(imgUrl);

        } catch (IOException e) {

            throw new RuntimeException(e);
        }
        user.setFace(imgUrl);

        return ResponseEntity.status(HttpStatus.CREATED).body(userRepository.save(user));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") UUID id) {
        Optional<UserModel> SearchType = userRepository.findById(id);
        if (SearchType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }
        SearchType.ifPresent(userRepository::delete);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
    }
}
