package com.wessim.event_scheduler_api.services;

import com.wessim.event_scheduler_api.models.Event;
import com.wessim.event_scheduler_api.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private EventRepository eventRepository;

    @Scheduled(fixedRate = 30000)
    public void simulateNotifications() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime inFiveMinutes = now.plusMinutes(5);

        List<Event> upcoming = eventRepository.findByDatetimeBetweenAndNotified(now, inFiveMinutes, false);

        for (Event event : upcoming) {
            System.out.println("Notification: Event '" + event.getTitle() +
                    "' is starting at " + event.getDatetime());

            event.setNotified(true);
            eventRepository.save(event);
        }
    }
}
