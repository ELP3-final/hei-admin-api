package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.validator.EventValidator;
import school.hei.haapi.repository.EventRepository;


import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    private final EventValidator validator;

    public Event getById(String id){
        return eventRepository.getById(id);
    }

    public List<Event> getAllEvent(
            PageFromOne page, BoundedPageSize pageSize,
            school.hei.haapi.endpoint.rest.model.Event.StatusEnum status) {
        List<Event> allEvent = eventRepository.findAll();
        if (status != null) {
            return getEventByStatus(allEvent,status, page, pageSize);
        }
        return getEventByStatus(allEvent,
                school.hei.haapi.endpoint.rest.model.Event.StatusEnum.ONGOING, page, pageSize);
    }

    private List<Event> getEventByStatus(
            List<Event> event,
            school.hei.haapi.endpoint.rest.model.Event.StatusEnum status,
            PageFromOne page, BoundedPageSize pageSize) {
        int firstIndex = 0;
        int lastIndex = page.getValue() * pageSize.getValue();
        if (page.getValue() > 1) {
            firstIndex = (page.getValue() - 1) * pageSize.getValue();
        }
        List<Event> eventByStatus = event.stream()
                .filter(events -> events.getStatus().equals(status))
                .collect(Collectors.toUnmodifiableList());
        if (firstIndex >= eventByStatus.size()) {
            return List.of();
        }
        if (lastIndex > eventByStatus.size() - 1) {
            lastIndex = eventByStatus.size();
        }
        return eventByStatus.subList(firstIndex, lastIndex);
    }

    @Transactional
    public List<Event> createEvent(List<Event> events){
        validator.accept(events);
        return eventRepository.saveAll(events);
    }
}
