package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.MachineModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MachineRepository extends JpaRepository<MachineModel, UUID> {
}