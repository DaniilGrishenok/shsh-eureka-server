package com.shsh.eureka_server.repository;

import com.shsh.eureka_server.models.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AdminRepository extends JpaRepository<Admin, UUID> {
    Optional<Admin> findByUsername(String username);

    boolean existsByUsername(String username);
}
