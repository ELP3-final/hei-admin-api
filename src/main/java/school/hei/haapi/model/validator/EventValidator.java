package school.hei.haapi.model.validator;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.Event;
import school.hei.haapi.model.Place;
import school.hei.haapi.model.exception.BadRequestException;


import java.util.List;
import java.util.function.Consumer;

@Component
public class EventValidator implements Consumer<Event> {

    @Override
    public void accept(Event event) {
        if (event.getName() == null) {
            throw new BadRequestException("Student is mandatory");
        }
    }

    public void accept(List<Event> events) {
        events.forEach(this::accept);
    }
}

