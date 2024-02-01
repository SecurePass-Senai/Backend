package com.securepass.apisecurepass.repositories;

import com.securepass.apisecurepass.models.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {
}