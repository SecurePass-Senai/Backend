package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.dtos.LoginDto;
import com.securepass.apisecurepass.models.LoginModel;
import com.securepass.apisecurepass.repositories.LoginRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class LoginController {

    @Autowired
    LoginRepository loginRepository;

    @PostMapping
    public ResponseEntity<Object> Create(@RequestBody @Valid LoginDto loginDto) {

        LoginModel login = new LoginModel();

        BeanUtils.copyProperties(loginDto, login);

        return ResponseEntity.status(HttpStatus.CREATED).body(loginRepository.save(login));
    }

}
