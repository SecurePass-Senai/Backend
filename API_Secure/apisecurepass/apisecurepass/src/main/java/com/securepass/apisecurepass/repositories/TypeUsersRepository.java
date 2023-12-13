package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.TypeUsersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TypeUsersRepository extends JpaRepository<TypeUsersModel, UUID> {

}
