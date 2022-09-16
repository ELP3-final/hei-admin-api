package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
import school.hei.haapi.endpoint.rest.model.CreateFee;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.Fee;
import school.hei.haapi.model.Place;
import school.hei.haapi.model.User;
import school.hei.haapi.model.exception.BadRequestException;
import school.hei.haapi.model.exception.NotFoundException;
import school.hei.haapi.service.EventService;
import school.hei.haapi.service.PlaceService;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;


@Component
@AllArgsConstructor
public class EventMapper {
    private final PlaceService placeService;
 public school.hei.haapi.endpoint.rest.model.Event toRestEvent(Event event) {
     var restEvent = new school.hei.haapi.endpoint.rest.model.Event();
     restEvent.setId(event.getId());
     restEvent.setName(event.getName());
     restEvent.setType(event.getType());
     restEvent.setDate(Instant.from(event.getDate()));
     restEvent.setStartTime(event.getStartTime());
     restEvent.setFinishTime(event.getFinishTime());
     restEvent.setStatus(event.getStatus());
     restEvent.setPlaceId(restEvent.getPlaceId());

     return restEvent;
 }
    public Event toDomainEvent(Place place, CreateEvent restEvent) {
     return Event.builder()
                .name(restEvent.getName())
                .type(school.hei.haapi.endpoint.rest.model.Event.TypeEnum.valueOf(restEvent.getType().toString()))
                .startTime(restEvent.getStartTime())
                .finishTime(restEvent.getFinishTime())
                .status(school.hei.haapi.endpoint.rest.model.Event.StatusEnum.valueOf(restEvent.getType().toString()))
                .place(place)
                .build();
    }

    public List<Event> toDomainEvent(String placeId, List<CreateEvent> toCreate) {
        Place place = placeService.getById(placeId);
        if (place == null) {
            throw new NotFoundException("Place.id=" + placeId + " is not found");
        }
        return toCreate
                .stream()
                .map(createEvent -> toDomainEvent(place, createEvent))
                .collect(toUnmodifiableList());
    }
}

