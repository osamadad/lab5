package com.tuwaiq.lab5_eventsystem.Controller;

import Api.ApiResponse;
import com.tuwaiq.lab5_eventsystem.Model.Event;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/event-system")
public class EventController {
    ArrayList<Event> events = new ArrayList<>();

    @PostMapping("/add")
    public ApiResponse addEvent(@RequestBody Event event) {
        for (Event event1 : events) {
            if (event1.getId().equalsIgnoreCase(event.getId())) {
                return new ApiResponse("Please enter a valid ID");
            }
        }
        this.events.add(event);
        return new ApiResponse("The event have been added successfully");
    }

    @PostMapping("/add/now-date")                                       /* added as extra */
    public ApiResponse addEventNowDate(@RequestBody Event event) {
        event.setStartDate(LocalDateTime.now());
        this.events.add(event);
        return new ApiResponse("The event have been added successfully");
    }

    @GetMapping("/get")
    public ArrayList<Event> getEvents() {
        return events;
    }

    @PutMapping("/update/{index}")
    public ApiResponse updateEvent(@PathVariable int index, @RequestBody Event event) {
        if (events.size() - 1 < index) {
            return new ApiResponse("Event not found");
        }
        events.set(index, event);
        return new ApiResponse("The event have been updated successfully");
    }

    @DeleteMapping("/delete/{index}")
    public ApiResponse deleteEvent(@PathVariable int index) {
        if (events.size() - 1 < index) {
            return new ApiResponse("Event not found");
        }
        events.remove(index);
        return new ApiResponse("The event have been deleted successfully");

    }

    @PutMapping("/update/{id}/{capacity}")
    public ApiResponse changeCapacity(@PathVariable String id, @PathVariable int capacity) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                event.setCapacity(capacity);
                return new ApiResponse("The event capacity have been changes successfully");
            }
        }
        return new ApiResponse("Event not found");
    }

    @GetMapping("/get/{id}")
    public Event getEventById(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return event;
            }
        }
        return null;
    }

    @GetMapping("/get/start-date/{id}")                         /* added as extra */
    public LocalDateTime getEventStartDate(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return event.getStartDate();
            }
        }
        return null;
    }

    @GetMapping("/get/end-date/{id}")                           /* added as extra */
    public LocalDateTime getEventEndDate(@PathVariable String id) {
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                return event.getEndDate();
            }
        }
        return null;
    }

    @GetMapping("/get/duration/{id}")                           /* added as extra */
    public ApiResponse getEventDuration(@PathVariable String id) {
        Duration duration;
        for (Event event : events) {
            if (event.getId().equalsIgnoreCase(id)) {
                duration = Duration.between(event.getStartDate(), event.getEndDate());
                double days = duration.toDays();
                double hours= duration.toHours()-(days*24);
                double minutes= duration.toMinutes() - (hours*60)-(days*24*60);
                return new ApiResponse("The duration of this event is " + days + " days and " + hours + " hours and " + minutes + " minutes");
            }
        }
        return new ApiResponse("Event not found");
    }

}
