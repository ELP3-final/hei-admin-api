package school.hei.haapi.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import school.hei.haapi.model.Place;
import school.hei.haapi.model.validator.PlaceValidator;
import school.hei.haapi.repository.PlaceRepository;

import java.util.List;

@Service
@AllArgsConstructor
public class PlaceService {
    private PlaceRepository repository;
    private PlaceValidator validator;

    public List<Place> getAll() {
        return repository.findAll();
    }

    public Place getById(String id) {
        return repository.getById(id);
    }

    @Transactional
    public List<Place> saveAll(List<Place> toSave) {
        validator.accept(toSave);
        return repository.saveAll(toSave);
    }
}
