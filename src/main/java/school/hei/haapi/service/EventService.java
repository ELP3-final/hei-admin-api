package school.hei.haapi.service;

import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import school.hei.haapi.model.*;
import school.hei.haapi.model.validator.PaymentValidator;
import school.hei.haapi.repository.EventParticipantRepository;
import school.hei.haapi.repository.EventRepository;
import school.hei.haapi.repository.PaymentRepository;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@AllArgsConstructor
public class EventService {
    private final EventRepository eventRepository;

    public Event getEvent(
            String id) {
        return eventRepository.findById(id).orElse(null);}

}
