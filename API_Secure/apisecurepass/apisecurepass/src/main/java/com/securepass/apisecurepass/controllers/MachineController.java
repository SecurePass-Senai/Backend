package com.securepass.apisecurepass.controllers;

import com.securepass.apisecurepass.dtos.MachineDto;
import com.securepass.apisecurepass.models.MachineModel;
import com.securepass.apisecurepass.models.UserModel;
import com.securepass.apisecurepass.repositories.MachineRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(value = "/machine", produces = {"application/json"})
public class MachineController {

    @Autowired
    MachineRepository machineRepository;

    @GetMapping
    public ResponseEntity<List<MachineModel>> listMachine() {
        return ResponseEntity.status(HttpStatus.OK).body(machineRepository.findAll());
    }

    // Endpoint para cadastrar um novo usuário


    @PostMapping
    public ResponseEntity<Object> create(@RequestBody @Valid MachineDto machineDto) {

        MachineModel machine = new MachineModel();
        BeanUtils.copyProperties(machineDto, machine);
        MachineModel savedMachine = machineRepository.save(machine);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMachine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> update(@PathVariable(value = "id") UUID id, @RequestBody @Valid MachineDto machineDto) {
        Optional<MachineModel> searchMachine = machineRepository.findById(id);
        if (searchMachine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Máquina não encontrada");
        }

        MachineModel machine = searchMachine.get();
        BeanUtils.copyProperties(machineDto, machine);

        return ResponseEntity.status(HttpStatus.OK).body(machineRepository.save(machine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> delete(@PathVariable(value = "id") UUID id) {
        Optional<MachineModel> searchMachine = machineRepository.findById(id);
        if (searchMachine.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Máquina não encontrada");
        }

        searchMachine.ifPresent(machineRepository::delete);

        return ResponseEntity.status(HttpStatus.OK).body("Máquina deletada");
    }



}
