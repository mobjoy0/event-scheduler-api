package com.wessim.event_scheduler_api;

import com.wessim.event_scheduler_api.models.Event;
import com.wessim.event_scheduler_api.repositories.EventRepository;
import com.wessim.event_scheduler_api.services.NotificationService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class NotificationServiceTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private NotificationService notificationService;

    private Event event;

    @BeforeEach
    void setUp() {
        event = new Event();
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
    }

    @Test
    void upcomingEvent_shouldBeMarkedAsNotified() {
        // Arrange: Create a new event starting in 4 minutes
        event.setTitle("Soon Event");
        event.setDescription("This event is soon");
        event.setDatetime(LocalDateTime.now().plusMinutes(4));
        event.setNotified(false);
        event = eventRepository.save(event);

        // Act
        notificationService.processUpcomingEvents(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        // Assert
        Event updated = eventRepository.findById(event.getId()).orElseThrow();
        assertTrue(updated.isNotified(), "Event should have been marked as notified");
    }

    @Test
    void alreadyNotifiedEvent_shouldRemainNotified() {
        event.setTitle("Already Notified");
        event.setDescription("Already done");
        event.setDatetime(LocalDateTime.now().plusMinutes(3));
        event.setNotified(true);
        event = eventRepository.save(event);

        notificationService.processUpcomingEvents(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        Event updated = eventRepository.findById(event.getId()).orElseThrow();
        assertTrue(updated.isNotified(), "Already-notified event should remain notified");
    }

    @Test
    void upcomingEvent_shouldNotBeNotified() {
        // Arrange: Create a new event starting in 10 minutes
        event.setTitle("Later Event");
        event.setDescription("This event is not soon");
        event.setDatetime(LocalDateTime.now().plusMinutes(10));
        event.setNotified(false);
        event = eventRepository.save(event);

        notificationService.processUpcomingEvents(LocalDateTime.now(), LocalDateTime.now().plusMinutes(5));

        // Assert
        Event updated = eventRepository.findById(event.getId()).orElseThrow();
        assertFalse(updated.isNotified(), "Event should NOT have been marked as notified");
    }

}
