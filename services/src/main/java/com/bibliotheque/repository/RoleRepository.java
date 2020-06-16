package com.bibliotheque.repository;

import com.bibliotheque.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByRolNom(String role);
}
