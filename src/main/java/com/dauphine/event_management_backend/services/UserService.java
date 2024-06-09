package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.User;

import java.util.Optional;
import java.util.UUID;
import java.util.List;

public interface UserService {
    // Créer un nouvel utilisateur
    User createUser(String userName, String email, String password);

    // Mettre à jour un utilisateur existant
    User updateUser(UUID userId, String userName) throws UserNotFoundException;

    // Supprimer un utilisateur par son ID
    void deleteUser(UUID userId) throws UserNotFoundException;

    // Récupérer un utilisateur par son ID
    User getUserById(UUID userId) throws UserNotFoundException;

    // Récupérer tous les utilisateurs
    List<User> getAllUsers();

    // Rechercher des utilisateurs par leur nom d'utilisateur
    List<User> findUsersByUsername(String username);

    // Rechercher des utilisateurs par leur email
    Optional<User> findUserByEmail(String email);

    // Authentifier un utilisateur
    Optional<User> authenticateUser(String email, String password);
}
