package school.hei.haapi.endpoint.rest.controller;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.endpoint.rest.model.EventParticipant;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.service.EventParticipantService;
import school.hei.haapi.service.EventService;
import school.hei.haapi.service.PaymentService;
import school.hei.haapi.endpoint.rest.model.CreateOrUpdateEventParticipant;
import school.hei.haapi.endpoint.rest.mapper.EventParticipantMapper;
import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
public class EventParticipantController {

    private final EventParticipantService eventParticipantService;
    private final EventParticipantMapper eventParticipantMapper;

    @PutMapping("/events/{event_id}/event_participants")
    public List<EventParticipant> createEventParticipant(
            @PathVariable String event_id,
            @RequestBody List<CreateOrUpdateEventParticipant> toCreate) {
        return eventParticipantService
                .saveAll(eventParticipantMapper.toDomainEventParticipant(event_id, toCreate))
                .stream()
                .map(eventParticipantMapper::toRestEventParticipant)
                .collect(toUnmodifiableList());

    }

    @GetMapping("/events/{event_id}/event_participants")
    public List<EventParticipant> getEventParticipants(
            @PathVariable String event_id){
            return eventParticipantService.getEventParticipants(event_id).stream()
                    .map(eventParticipantMapper::toRestEventParticipant)
                    .collect(toUnmodifiableList());
                    }

}