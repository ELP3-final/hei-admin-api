package school.hei.haapi.endpoint.rest.mapper;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import school.hei.haapi.model.EventParticipant;
import java.util.List;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.User;
import school.hei.haapi.service.EventService;
import school.hei.haapi.service.UserService;
import school.hei.haapi.model.exception.NotFoundException;
import static java.util.stream.Collectors.toUnmodifiableList;
import school.hei.haapi.endpoint.rest.model.CreateOrUpdateEventParticipant;
import school.hei.haapi.endpoint.rest.validator.CreateCourseValidator;

@Component
@AllArgsConstructor
public class EventParticipantMapper {
    private final EventService eventService;
    private final UserService userService;

    private final UserMapper userMapper;
    public school.hei.haapi.endpoint.rest.model.EventParticipant toRestEventParticipant(EventParticipant eventParticipant) {
        school.hei.haapi.endpoint.rest.model.EventParticipant restEventParticipant =  new school.hei.haapi.endpoint.rest.model.EventParticipant();
        restEventParticipant.setId(eventParticipant.getId());
        restEventParticipant.setAttendance(eventParticipant.getAttendance());
        restEventParticipant.setEventRole(eventParticipant.getEventRole());
        restEventParticipant.setUser(userMapper.toRestUser(eventParticipant.getUser()));
        return restEventParticipant;
    }

    public EventParticipant toDomainEventParticipant(Event event,CreateOrUpdateEventParticipant restEventParticipant){
        User user = userService.getById(restEventParticipant.getUserId());
        if(user == null){
            throw new NotFoundException("User "+restEventParticipant.getUserId()+"not found");
        }
        return EventParticipant.builder()
                .id(restEventParticipant.getId())
                .attendance(toDomainAttendance(restEventParticipant.getAttendance()))
                .eventRole(toDomainEventRole(restEventParticipant.getEventRole()))
                .event(event)
                .user(user)
                .build();
    }

    public List<EventParticipant> toDomainEventParticipant(String eventId,List<CreateOrUpdateEventParticipant> restEventParticipants) {
        Event event = eventService.getById(eventId);
        if(event == null){
            throw new NotFoundException("Event with id : "+eventId+" not found");
        }
        return restEventParticipants.stream().
                map(createEventParticipant -> toDomainEventParticipant(event,createEventParticipant))
                .collect(toUnmodifiableList());

    }

    public school.hei.haapi.endpoint.rest.model.EventParticipant.AttendanceEnum toDomainAttendance(CreateOrUpdateEventParticipant.AttendanceEnum attendance){
        switch(attendance){
            case MISSING:
                return school.hei.haapi.endpoint.rest.model.EventParticipant.AttendanceEnum.MISSING;
            case HERE:
                return school.hei.haapi.endpoint.rest.model.EventParticipant.AttendanceEnum.HERE;
            case EXPECTED:
                return school.hei.haapi.endpoint.rest.model.EventParticipant.AttendanceEnum.EXPECTED;
            default:
                return null;
        }
    }  

    public school.hei.haapi.endpoint.rest.model.EventParticipant.EventRoleEnum toDomainEventRole(CreateOrUpdateEventParticipant.EventRoleEnum eventRole){
        switch(eventRole) {
            case SUPERVISOR:
                return school.hei.haapi.endpoint.rest.model.EventParticipant.EventRoleEnum.SUPERVISOR;
            case ATTENDANT:
                return school.hei.haapi.endpoint.rest.model.EventParticipant.EventRoleEnum.ATTENDANT;
            default:
                return null;
        }
    } 

}