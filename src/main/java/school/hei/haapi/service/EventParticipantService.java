package school.hei.haapi.service;

import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.BoundedPageSize;
import school.hei.haapi.model.EventParticipant;
import school.hei.haapi.model.PageFromOne;
import school.hei.haapi.model.Payment;
import school.hei.haapi.model.validator.PaymentValidator;
import school.hei.haapi.repository.EventParticipantRepository;
import school.hei.haapi.repository.PaymentRepository;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class EventParticipantService {

    private final EventParticipantRepository eventParticipantRepository;

    public List<EventParticipant> getEventParticipants(
            String eventId) {
        return eventParticipantRepository.findByEventId(eventId);}

    @Transactional
    public List<EventParticipant> saveAll(List<EventParticipant> toCreate) {
        return eventParticipantRepository.saveAll(toCreate);
    }
}