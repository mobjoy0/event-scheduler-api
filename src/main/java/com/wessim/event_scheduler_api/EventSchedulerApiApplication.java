package com.wessim.event_scheduler_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EventSchedulerApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EventSchedulerApiApplication.class, args);
	}

}
