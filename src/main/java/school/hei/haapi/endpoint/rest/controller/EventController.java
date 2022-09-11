package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.EventMapper;
import school.hei.haapi.endpoint.rest.model.Fee;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.EventService;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
public class EventController {
    private final EventService  eventService;
    private final EventMapper eventMapper;

    @GetMapping("/events")
    public List<Event> getEvent(
            @RequestParam PageFromOne page,
            @RequestParam("page_size") BoundedPageSize pageSize,
            @RequestParam(required = false) Event.StatusEnum status) {
        return eventService.getAllEvent(page, pageSize, status).stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable String id){
        return eventService.getById(id);
    }

    @PutMapping("/events")
    public List<Event> createOrUpdate(@RequestBody List<Event> events){
        var saved = eventService.createEvent(events.stream()
                .map(eventMapper::toDomainEvent)
                .collect(toUnmodifiableList()));
        return saved.stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }
}
