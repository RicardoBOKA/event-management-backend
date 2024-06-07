package com.dauphine.event_management_backend.repository;

import com.dauphine.event_management_backend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    // Rechercher des utilisateurs par leur nom d'utilisateur
    @Query("""
        SELECT u
        FROM User u
        WHERE UPPER(u.userName) LIKE UPPER(CONCAT('%', :username, '%'))
    """)
    List<User> findUsersByUsername(@Param("username") String username);

    // Rechercher des utilisateurs par leur email
    @Query("""
        SELECT u
        FROM User u
        WHERE UPPER(u.email) = UPPER(:email)
    """)
    Optional<User> findUserByEmail(@Param("email") String email);
}
