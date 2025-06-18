package com.wessim.event_scheduler_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wessim.event_scheduler_api.models.Event;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EventControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createEvent_shouldReturn200() throws Exception {
        Event event = new Event();
        event.setTitle("Test Event");
        event.setDescription("Test Description");
        event.setDatetime(LocalDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void getAllEvents_shouldReturn200() throws Exception {
        mockMvc.perform(get("/events"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
    }

    @Test
    void createEvent_withMissingTitle_shouldReturn400() throws Exception {
        Event event = new Event();
        event.setDescription("Missing title");
        event.setDatetime(LocalDateTime.now().plusMinutes(10));

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(event)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createEvent_invalidDatetime_shouldReturn400() throws Exception {
        String invalidJson = """
        {
          "title": "Test Event",
          "description": "Invalid Datetime",
          "datetime": "not-a-valid-datetime"
        }
        """;

        mockMvc.perform(post("/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson))
                .andExpect(status().isBadRequest());
    }

}
