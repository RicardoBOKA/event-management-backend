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
    WHERE (cast(:startDate AS date) IS NULL OR e.startEvent >= :startDate)
    AND (cast(:endDate AS date) IS NULL OR e.endEvent <= :endDate)
""")
    List<Event> searchEvents(@Param("startDate") LocalDateTime startDate,
                             @Param("endDate") LocalDateTime endDate);



    @Query("""
        SELECT e
        FROM Event e
        WHERE UPPER(e.eventName) LIKE UPPER(CONCAT('%', :name, '%'))
""")
    List<Event> findAllLikeNames(@Param("name") String name);
}

