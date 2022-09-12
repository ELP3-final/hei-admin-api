package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.EventMapper;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
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
    public List<school.hei.haapi.endpoint.rest.model.Event> getEvent(
            @RequestParam PageFromOne page,
            @RequestParam("page_size") BoundedPageSize pageSize,
            @RequestParam(required = false) String status) {
        return eventService.getAllEvent(page, pageSize, school.hei.haapi.endpoint.rest.model.Event.StatusEnum.valueOf(status)).stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/events/{id}")
    public Event getEventById(@PathVariable String id){
        return eventService.getById(id);
    }

    @PutMapping("/events")
    public List<school.hei.haapi.endpoint.rest.model.Event> createOrUpdate(@RequestBody List<CreateEvent> toCreate){
        var saved = eventService.createEvent(toCreate.stream()
                .map((CreateEvent restEvent) -> eventMapper.toDomainEvent(restEvent))
                .collect(toUnmodifiableList()));
        return saved.stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }
}
