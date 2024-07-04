package com.dauphine.event_management_backend.services;

import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.User;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
/**
 * Service interface for managing users.
 */
public interface UserService {

    /**
     * Creates a new user with the specified details.
     *
     * @param userName the username of the user
     * @param email    the email address of the user
     * @param password the password of the user
     * @return the created user object
     */
    User createUser(String userName, String email, String password);


    /**
     * Updates an existing user's username.
     *
     * @param userId   the ID of the user to update
     * @param userName the new username for the user
     * @return the updated user object
     * @throws UserNotFoundException if the user is not found
     */
    User updateUser(UUID userId, String userName) throws UserNotFoundException;

    /**
     * Deletes a user by its ID.
     *
     * @param userId the ID of the user to delete
     * @throws UserNotFoundException if the user is not found
     */
    void deleteUser(UUID userId) throws UserNotFoundException;


    /**
     * Retrieves a user by its ID.
     *
     * @param userId the ID of the user to retrieve
     * @return the user object
     * @throws UserNotFoundException if the user is not found
     */
    User getUserById(UUID userId) throws UserNotFoundException;

    /**
     * Retrieves all users.
     *
     * @return the list of all users
     */
    List<User> getAllUsers();

    /**
     * Finds users by their username.
     *
     * @param username the username to search for
     * @return the list of users matching the username
     */
    List<User> findUsersByUsername(String username);

    /**
     * Finds a user by their email address.
     *
     * @param email the email address to search for
     * @return an optional containing the found user, or empty if not found
     */
    Optional<User> findUserByEmail(String email);

    /**
     * Authenticates a user with the provided email and password.
     *
     * @param email    the email address of the user to authenticate
     * @param password the password of the user
     * @return an optional containing the authenticated user, or empty if authentication fails
     */
    Optional<User> authenticateUser(String email, String password);
}
