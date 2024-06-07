package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.models.User;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserService {
    // Créer un nouvel utilisateur
    User createUser(User user);

    // Mettre à jour un utilisateur existant
    User updateUser(UUID userId, String userName);

    // Supprimer un utilisateur par son ID
    void deleteUser(UUID userId);

    // Récupérer un utilisateur par son ID
    Optional<User> getUserById(UUID userId);

    // Récupérer tous les utilisateurs
    List<User> getAllUsers();

    // Rechercher des utilisateurs par leur nom d'utilisateur
    List<User> findUsersByUsername(String username);

    // Rechercher des utilisateurs par leur email
    Optional<User> findUserByEmail(String email);

    // Authentifier un utilisateur
    //Optional<User> authenticateUser(String email, String password);
}
