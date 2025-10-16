package com.dauphine.event_management_backend.controller;

import com.dauphine.event_management_backend.dto.UserRequest;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
/**
 * Controller for handling requests related to users.
 */
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    /**
     * Constructor to initialize the user service.
     *
     * @param userService the user service to be used.
     */
    public UserController(UserService userService) {
        this.userService = userService;
    }
    /**
     * Create a new user.
     *
     * @param userRequest the user request containing user details.
     * @return the created user.
     */
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequest userRequest) {
        logger.info("[API] POST /v1/users - Création d'un nouvel utilisateur");
        User createdUser = userService.createUser(userRequest.getUserName(), userRequest.getEmail(), userRequest.getPassword());
        logger.info("[API] POST /v1/users - Utilisateur créé avec succès (ID: {})", createdUser.getUserId());
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }
    /**
     * Update an existing user.
     *
     * @param userId the UUID of the user to be updated.
     * @param userRequest the user request containing updated user details.
     * @return the updated user.
     */
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable UUID userId, @RequestBody UserRequest userRequest) {
        try {
            User updatedUser = userService.updateUser(userId, userRequest.getUserName());
            return ResponseEntity.ok(updatedUser);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
    /**
     * Delete an existing user.
     *
     * @param userId the UUID of the user to be deleted.
     * @return a response indicating the result of the deletion.
     */
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable UUID userId) {
        try {
            userService.deleteUser(userId);
            return ResponseEntity.noContent().build();
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    /**
     * Retrieve a user by its ID.
     *
     * @param userId the UUID of the user to be retrieved.
     * @return the user with the specified ID.
     */
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable UUID userId) {
        try {
            User user = userService.getUserById(userId);
            return ResponseEntity.ok(user);
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
    /**
     * Retrieve all users.
     *
     * @return a list of all users.
     */
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
    /**
     * Find users by username.
     *
     * @param userName the username to search for.
     * @return a list of users with the specified username.
     */
    @GetMapping("/search/by-username")
    public ResponseEntity<List<User>> findUsersByUsername(@RequestParam String userName) {
        List<User> users = userService.findUsersByUsername(userName);
        return ResponseEntity.ok(users);
    }
    /**
     * Find a user by email.
     *
     * @param email the email to search for.
     * @return the user with the specified email.
     */
    @GetMapping("/search/by-email")
    public ResponseEntity<User> findUserByEmail(@RequestParam String email) {
        Optional<User> user = userService.findUserByEmail(email);
        return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    /**
     * Authenticate a user.
     *
     * @param userRequest the user request containing email and password.
     * @return the authenticated user.
     */
    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestBody UserRequest userRequest) {
        logger.info("[API] POST /v1/users/authenticate - Tentative d'authentification");
        
        Optional<User> user = userService.authenticateUser(userRequest.getEmail(), userRequest.getPassword());
        
        if (user.isPresent()) {
            logger.info("[API] POST /v1/users/authenticate - Authentification réussie pour l'utilisateur ID: {}", user.get().getUserId());
            return ResponseEntity.ok(user.get());
        } else {
            logger.warn("[API] POST /v1/users/authenticate - Authentification échouée - identifiants invalides");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
