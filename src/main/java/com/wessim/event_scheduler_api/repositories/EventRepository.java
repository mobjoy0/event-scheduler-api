package com.wessim.event_scheduler_api.repositories;

import com.wessim.event_scheduler_api.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByDatetimeBetween(LocalDateTime from, LocalDateTime to);
}
