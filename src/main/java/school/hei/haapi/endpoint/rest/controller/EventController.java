package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.EventMapper;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
import school.hei.haapi.endpoint.rest.model.CreateFee;
import school.hei.haapi.endpoint.rest.model.Event;
import school.hei.haapi.endpoint.rest.model.Fee;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.EventService;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
@CrossOrigin
public class EventController {
    private final EventService  eventService;
    private final EventMapper eventMapper;

    @GetMapping("/events")
    public List<school.hei.haapi.endpoint.rest.model.Event> getEvents(
            @RequestParam PageFromOne page,
            @RequestParam("page_size") BoundedPageSize pageSize,
            @RequestParam(required = false) String status) {
        return eventService.getAllEvent(page, pageSize, school.hei.haapi.endpoint.rest.model.Event.StatusEnum.valueOf(status)).stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }

    @GetMapping("/events/{id}")
    public Event getCourseById(@PathVariable("id") String id) {
        return eventMapper.toRestEvent(eventService.getById(id));
    }

    @PutMapping("/places/{place_id}/events")
    public List<Event> createFees(
            @PathVariable String placeId, @RequestBody List<CreateEvent> toCreate) {
        return eventService.createEvent(
                        eventMapper.toDomainEvent(placeId, toCreate)).stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }
   /* @PutMapping("/events")
    public List<Event> createOrUpdateEvents
            (@RequestBody List<Event> toCreate){
        var saved = eventService.createEvent(toCreate.stream()
                .map((Event restEvent) -> eventMapper.toDomainEvent(restEvent))
                .collect(toUnmodifiableList()));
        return saved.stream()
                .map(eventMapper::toRestEvent)
                .collect(toUnmodifiableList());
    }*/

}
