package com.dauphine.event_management_backend.repository;

import com.dauphine.event_management_backend.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface EventRepository extends JpaRepository<Event, UUID> {
    @Query("SELECT e FROM Event e WHERE e.user.userId = :organizerId")
    List<Event> findEventsByOrganizer(@Param("organizerId") UUID organizerId);

    @Query("""
    SELECT e FROM Event e
    WHERE (:startDate IS NULL OR e.startEvent >= :startDate)
    AND (:endDate IS NULL OR e.endEvent <= :endDate)
    AND (:name IS NULL OR LOWER(e.eventName) LIKE LOWER(CONCAT('%', :name, '%')))
    AND (:location IS NULL OR e.location = :location)
""")
    List<Event> searchEvents(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate,
                             @Param("name") String name,
                             @Param("location") String location);
}

