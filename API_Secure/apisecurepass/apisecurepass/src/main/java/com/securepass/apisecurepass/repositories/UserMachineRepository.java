package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.UserMachineModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserMachineRepository extends JpaRepository<UserMachineModel, UUID> {
}