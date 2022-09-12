package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
import school.hei.haapi.model.Event;

import java.time.Instant;


@Component
@AllArgsConstructor
public class EventMapper {
    public school.hei.haapi.endpoint.rest.model.Event toRestEvent(Event event) {
        var restEvent = new school.hei.haapi.endpoint.rest.model.Event();
            restEvent.setId(event.getId());
            restEvent.setName(event.getName());
            restEvent.setType(event.getType());
            restEvent.setDate(Instant.from(event.getDate()));
            restEvent.setStartTime(event.getStartTime());
            restEvent.setFinishTime(event.getFinishTime());
            restEvent.setSupervisor(event.getSupervisor());
            restEvent.setStatus(event.getStatus());
            restEvent.setGroupId(restEvent.getGroupId());
            restEvent.setPlaceId(restEvent.getPlaceId());

            return restEvent;
    }

    public Event toDomainEvent(CreateEvent restEvent) {
        return Event.builder()
                .name(restEvent.getName())
                .type(school.hei.haapi.endpoint.rest.model.Event.TypeEnum.valueOf(restEvent.getType().toString()))
                .startTime(restEvent.getStartTime())
                .finishTime(restEvent.getFinishTime())
                .supervisor(school.hei.haapi.endpoint.rest.model.Event.SupervisorEnum.valueOf(restEvent.getType().toString()))
                .status(school.hei.haapi.endpoint.rest.model.Event.StatusEnum.valueOf(restEvent.getType().toString()))
                .id(restEvent.getPlaceId())
                .id(restEvent.getGroupId())
                .build();
    }
}

