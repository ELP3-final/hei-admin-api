package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.endpoint.rest.model.CreateEvent;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.exception.BadRequestException;

import java.util.Objects;

@Component
@AllArgsConstructor
public class EventMapper {
    public Event toRestEvent(school.hei.haapi.model.Event event) {
        return new Event()
                .id(event.getId())
                .name(event.getName())
                .type(event.getType())
                .date(event.getDate())
                .startTime(event.getStartTime())
                .finishTime(event.getFinishTime())
                .supervisor(event.getSupervisor())
                .status(event.getStatus())
                .placeId(event.getPlace().getId())
                .groupId(event.getGroup().getId());

    }

    public school.hei.haapi.model.Event toDomainEvent(CreateEvent createEvent) {
        return school.hei.haapi.model.Event.builder()
                .type(toDomainEventType(Objects.requireNonNull(createEvent.getType())))
                .date()
                .startTime()
                .finishTime()
                .supervisor(toDomainEventSupervisor(Objects.requireNonNull(createEvent.getSupervisor())))
                .status(toDomainEventStatus(Objects.requireNonNull(createEvent.getStatus())))
                .build();
    }

    private Event.TypeEnum toDomainEventType(CreateEvent.TypeEnum createEventType) {
        switch (createEventType) {
            case COURSE:
                return Event.TypeEnum.COURSE;
            case OTHER:
                return Event.TypeEnum.OTHER;
            default:
                throw new BadRequestException("Unexpected eventType: " + createEventType.getValue());
        }
    }

    private Event.SupervisorEnum toDomainEventSupervisor(CreateEvent.SupervisorEnum createEventSupervisor) {
        switch (createEventSupervisor) {
            case TEACHER:
                return Event.SupervisorEnum.TEACHER;
            case ADMINISTRATOR:
                return Event.SupervisorEnum.ADMINISTRATOR;
            default:
                throw new BadRequestException("Unexpected eventSupervisor: " + createEventSupervisor.getValue());
        }
    }

    private Event.StatusEnum toDomainEventStatus(CreateEvent.StatusEnum createEventStatus) {
        switch (createEventStatus) {
            case ONGOING:
                return Event.StatusEnum.ONGOING;
            case CANCELED:
                return Event.StatusEnum.CANCELED;
            case FINISHED:
                return Event.StatusEnum.FINISHED;
            default:
                throw new BadRequestException("Unexpected eventStatus: " + createEventStatus.getValue());
        }
    }
}

