package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.dtos.UserMachineDto;
import com.securepass.apisecurepass.models.UserMachineModel;
import com.securepass.apisecurepass.repositories.UserMachineRepository;
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
@RequestMapping(value = "/user-machine", produces = {"application/json"})
public class UserMachineController {

    @Autowired
    UserMachineRepository userMachineRepository;


    @GetMapping
    public ResponseEntity<List<UserMachineModel>> ListTypesUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userMachineRepository.findAll());
    }


    @PostMapping
    public ResponseEntity<Object> Create(@RequestBody @Valid UserMachineDto userMachineDto) {

        UserMachineModel userMachine = new UserMachineModel();

        BeanUtils.copyProperties(userMachineDto, userMachine);

        return ResponseEntity.status(HttpStatus.CREATED).body(userMachineRepository.save(userMachine));
    }
}
