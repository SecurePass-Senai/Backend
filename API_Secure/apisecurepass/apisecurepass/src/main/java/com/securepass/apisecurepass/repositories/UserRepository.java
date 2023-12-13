package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.TypeUsersModel;
import com.securepass.apisecurepass.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserModel, UUID> {
    // Método para buscar um usuário pelo email
    UserModel findByEmail(String email);


}
