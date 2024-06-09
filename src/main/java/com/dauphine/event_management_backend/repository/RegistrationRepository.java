package com.dauphine.event_management_backend.repository;

import com.dauphine.event_management_backend.models.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<Registration, UUID> {
    //A REVOIR SI NECESSAIRE OU SI ON FAIT AVEC LE REPOSITORY...
    // Récupérer toutes les inscriptions pour un événement donné
    @Query("""
        SELECT r
        FROM Registration r
        WHERE r.event.eventId = :eventId
    """)
    List<Registration> getRegistrationsByEventId(@Param("eventId") UUID eventId);

    // Récupérer toutes les inscriptions d'un utilisateur
    @Query("""
        SELECT r
        FROM Registration r
        WHERE r.user.userId = :userId
    """)
    List<Registration> getRegistrationsByUserId(@Param("userId") UUID userId);

    // Vérifier si un utilisateur est inscrit à un événement spécifique
    @Query("""
        SELECT CASE WHEN COUNT(r) > 0 THEN true ELSE false END
        FROM Registration r
        WHERE r.user.userId = :userId AND r.event.eventId = :eventId
    """)
    boolean isUserRegistered(@Param("userId") UUID userId, @Param("eventId") UUID eventId);
}
