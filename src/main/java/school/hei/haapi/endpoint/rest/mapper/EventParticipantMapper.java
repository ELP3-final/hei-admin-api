package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.EventParticipant;


@Component
@AllArgsConstructor
public class EventParticipantMapper {

    public school.hei.haapi.endpoint.rest.model.EventParticipant toRestEventParticipant(EventParticipant eventParticipant) {
        school.hei.haapi.endpoint.rest.model.EventParticipant restEventParticipant =  new school.hei.haapi.endpoint.rest.model.EventParticipant();
        restEventParticipant.setId(eventParticipant.getId());
        restEventParticipant.setAttendance(eventParticipant.getAttendance());
        restEventParticipant.setEventRole(eventParticipant.getEventRole());
        restEventParticipant.setUser(eventParticipant.getUser());
        return restEventParticipant;
    }

    public EventParticipant toDomainEventParticipant(school.hei.haapi.endpoint.rest.model.EventParticipant restEventParticipant) {
        return EventParticipant.builder()
                .id(restEventParticipant.getId())
                .attendance(restEventParticipant.getAttendance())
                .eventRole(restEventParticipant.eventRole())
                .user(restEventParticipant.user())
                .build();
    }
}