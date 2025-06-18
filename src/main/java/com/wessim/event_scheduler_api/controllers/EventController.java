package com.wessim.event_scheduler_api.controllers;

import com.wessim.event_scheduler_api.models.Event;
import com.wessim.event_scheduler_api.repositories.EventRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @Operation(summary = "Create a new event", description = "Adds a new event to the system. Requires title, description, and datetime.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request body")
    })
    @PostMapping
    public Event createEvent(@Valid @RequestBody Event event) {

        event.setNotified(false);
        return eventRepository.save(event);
    }

    @Operation(summary = "List all events", description = "Returns a list of all scheduled events.")
    @ApiResponse(responseCode = "200", description = "List of events")
    @GetMapping
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Operation(summary = "Get a specific event by ID", description = "Fetches a single event by its unique ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")
    })
    @GetMapping("/{id}")
    public Event getEventById(
            @Parameter(description = "ID of the event to retrieve")
            @PathVariable Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
}
