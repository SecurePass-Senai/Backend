package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.dtos.TypeUsersDto;
import com.securepass.apisecurepass.models.TypeUsersModel;
import com.securepass.apisecurepass.repositories.TypeUsersRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/types", produces = {"application/json"})
public class TypeUsersController {
    @Autowired
    TypeUsersRepository typeUsersRepository;

    @GetMapping
    public ResponseEntity<List<TypeUsersModel>> ListTypesUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(typeUsersRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> UserById(@PathVariable(value = "id") UUID id) {
        Optional<TypeUsersModel> SearchType = typeUsersRepository.findById(id);

        if (SearchType.isEmpty()) {
            // Retornar usuario não encontrado
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado");
        }

        return ResponseEntity.status(HttpStatus.OK).body(SearchType.get());
    }



    @PostMapping
    public ResponseEntity<Object> Create(@RequestBody @Valid TypeUsersDto typeUsersDto) {

        TypeUsersModel typeUser = new TypeUsersModel();

        BeanUtils.copyProperties(typeUsersDto, typeUser);

        return ResponseEntity.status(HttpStatus.CREATED).body(typeUsersRepository.save(typeUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> Update(@PathVariable(value = "id") UUID id, @ModelAttribute @Valid TypeUsersDto typeUsersDto) {
        Optional<TypeUsersModel> SearchType = typeUsersRepository.findById(id);
        if (SearchType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo Usuário não encontrado");

        }
        TypeUsersModel typeUser = SearchType.get();

        BeanUtils.copyProperties(typeUsersDto, typeUser);

        return ResponseEntity.status(HttpStatus.OK).body(typeUsersRepository.save(typeUser));

    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> Delete(@PathVariable(value = "id") UUID id) {
        Optional<TypeUsersModel> SearchType = typeUsersRepository.findById(id);
        if (SearchType.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario nao encontrado");
        }
        SearchType.ifPresent(typeUsersRepository::delete);

        return ResponseEntity.status(HttpStatus.OK).body("Usuario deletado");
    }


}
