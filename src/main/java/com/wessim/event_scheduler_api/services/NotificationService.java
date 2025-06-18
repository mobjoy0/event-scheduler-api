package com.wessim.event_scheduler_api.services;

import com.wessim.event_scheduler_api.models.Event;
import com.wessim.event_scheduler_api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private EventRepository eventRepository;

    @Scheduled(fixedRate = 30000)
    @Profile("!test")
    public void simulateNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inFiveMinutes = now.plusMinutes(5);
        processUpcomingEvents(now, inFiveMinutes);
    }

    public void processUpcomingEvents(LocalDateTime from, LocalDateTime to) {
        List<Event> upcoming = eventRepository.findByDatetimeBetweenAndNotified(from, to, false);

        for (Event event : upcoming) {
            System.out.println("🔔 Notification: " + event.getTitle());
            event.setNotified(true);
            eventRepository.save(event);
        }
    }

}
