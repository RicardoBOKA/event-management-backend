package com.dauphine.event_management_backend.services.impl;

import com.dauphine.event_management_backend.exceptions.UserAlreadyExistsException;
import com.dauphine.event_management_backend.exceptions.UserNotFoundException;
import com.dauphine.event_management_backend.models.User;
import com.dauphine.event_management_backend.repository.UserRepository;
import com.dauphine.event_management_backend.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User createUser(String userName, String email, String password) {
        logger.info("[AUTH] Tentative de création d'utilisateur avec email: {}", email);
        
        // Validation des champs
        if (userName == null || userName.trim().isEmpty()) {
            logger.warn("[AUTH] Échec création utilisateur - nom d'utilisateur vide");
            throw new IllegalArgumentException("Le nom d'utilisateur est obligatoire");
        }
        
        if (email == null || email.trim().isEmpty()) {
            logger.warn("[AUTH] Échec création utilisateur - email vide");
            throw new IllegalArgumentException("L'email est obligatoire");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            logger.warn("[AUTH] Échec création utilisateur - format email invalide: {}", email);
            throw new IllegalArgumentException("Le format de l'email est invalide");
        }
        
        if (password == null || password.length() < 6) {
            logger.warn("[AUTH] Échec création utilisateur - mot de passe trop court");
            throw new IllegalArgumentException("Le mot de passe doit contenir au moins 6 caractères");
        }
        
        // Vérifier si l'email existe déjà
        if (userRepository.findUserByEmail(email).isPresent()) {
            logger.warn("[AUTH] Échec création utilisateur - email déjà existant: {}", email);
            throw new UserAlreadyExistsException(email);
        }
        
        User newUser = new User();
        newUser.setUserId(UUID.randomUUID());
        newUser.setUserName(userName.trim());
        newUser.setEmail(email.trim().toLowerCase());
        newUser.setPassword(passwordEncoder.encode(password));
        
        User savedUser = userRepository.save(newUser);
        logger.info("[AUTH] Utilisateur créé avec succès - ID: {}, Email: {}", savedUser.getUserId(), savedUser.getEmail());
        
        return savedUser;
    }


    @Override
    public User updateUser(UUID userId, String userName) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
        user.setUserName(userName);
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(UUID userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public User getUserById(UUID userId) throws UserNotFoundException {
        return userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> findUsersByUsername(String username) {
        return userRepository.findUsersByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> authenticateUser(String email, String password) {
        logger.info("[AUTH] Tentative de connexion pour l'email: {}", email);
        
        Optional<User> userOpt = userRepository.findUserByEmail(email);
        
        if (userOpt.isEmpty()) {
            logger.warn("[AUTH] Échec connexion - email inexistant: {}", email);
            return Optional.empty();
        }
        
        User user = userOpt.get();
        boolean passwordMatches = passwordEncoder.matches(password, user.getPassword());
        
        if (passwordMatches) {
            logger.info("[AUTH] Connexion réussie pour l'utilisateur: {} (ID: {})", user.getEmail(), user.getUserId());
            return Optional.of(user);
        } else {
            logger.warn("[AUTH] Échec connexion - mot de passe incorrect pour l'email: {}", email);
            return Optional.empty();
        }
    }
}
