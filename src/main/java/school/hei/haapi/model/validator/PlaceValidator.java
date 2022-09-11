package school.hei.haapi.model.validator;

import org.springframework.stereotype.Component;
import school.hei.haapi.model.Place;
import school.hei.haapi.model.exception.BadRequestException;

import java.util.List;
import java.util.function.Consumer;

@Component
public class PlaceValidator implements Consumer<Place> {
    @Override
    public void accept(Place tocheck) {
        if (tocheck.getLabel() == null) {
            throw new BadRequestException("label is mandatory");
        }
    }

    public void accept(List<Place> placesToCheck) {
        placesToCheck.forEach(this::accept);
    }
}
