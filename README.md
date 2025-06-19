
# Event Scheduler API

A simple Spring Boot REST API to manage events with notification simulation.

## Features

-   **POST /events** — Add a new event (title, description, datetime)
-   **GET /events** — List all events
-   **GET /events/{id}** — Get details of a single event
-   **Notification simulation** — Uses a scheduled background task with Spring Scheduler to periodically check for events starting within 5 minutes and simulate notifications by printing messages

## How to run

### Prerequisites

-   Java 23 installed
-   Maven installed
-   Docker (optional, if you want to run via container)

### Build and run locally

1.  Clone the repository:

```bash
git clone https://github.com/mobjoy0/event-scheduler-api
cd event-scheduler-api
```

2.  Build the project:

```bash
mvn clean package
```

3.  Run the application:

```bash
mvn spring-boot:run
```

4.  The API will be available at `http://localhost:9001`

### Run with Docker

```bash
docker build -t event-scheduler-api .
docker run -p 9001:9001 event-scheduler-api

```

## API Usage

### Add an event

```bash
curl -X POST http://localhost:9001/events \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Team Meeting",
    "description": "Weekly team sync",
    "datetime": "2025-06-18T15:30:00"
  }'

```

### List all events

```bash
curl http://localhost:9001/events

```

### Get specific event

```bash
curl http://localhost:9001/events/1

```
### Response Example

```json
{
  "id": 1,
  "title": "Team Meeting",
  "description": "Weekly team sync",
  "datetime": "2025-06-18T15:30:00"
}
```

## Technical Implementation

-   **Framework**: Spring Boot 3.x with Java 21
-   **Storage**: In-memory storage for simplicity
-   **Scheduler**: Spring's `@Scheduled` annotation runs every minute to check for upcoming events
-   **Notification**: Console logging simulation when events are within 5 minutes of start time
-   **DateTime Format**: ISO 8601 format (YYYY-MM-DDTHH:MM:SS)


## If I had more time, I would...

- Replace the in-memory storage with persistent database integration (e.g., PostgreSQL), including migrations for event schema.
- Improve error handling and validation (e.g., check for past dates, missing fields) to make the API more robust.
- Move the notification logic from a simple scheduler to a more scalable background queue system using Redis or RabbitMQ, to decouple job execution from the main app.
- Add proper timezone normalization to support international usage.

## If this had to serve 10,000 users a day, what would break?

This project is a functional prototype, but it is not yet ready for high-scale production. If it had to handle 10,000 users per day, several critical issues would arise:



### 🔸 Scheduled Notification Logic
- **Issue**: The `@Scheduled` task scans all events periodically.
- **Problem**: This approach becomes inefficient when scanning tens of thousands of records.
- **Impact**: CPU usage spikes, potential performance bottlenecks, or missed/delayed notifications.

---


### 🔸 No Persistent Database
- Issue: Data is stored only in memory (RAM), not in a persistent database.
- Problem: No indexing, no data durability, and no support for multi-instance deployments.
- Impact: All data is lost if the application restarts, querying performance degrades as data grows, and the application may crash or become unstable if memory usage exceeds available resources.