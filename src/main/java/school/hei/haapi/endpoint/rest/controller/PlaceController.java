package school.hei.haapi.endpoint.rest.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import school.hei.haapi.endpoint.rest.mapper.PlaceMapper;
import school.hei.haapi.endpoint.rest.model.Place;
import school.hei.haapi.service.PlaceService;

import java.util.List;

import static java.util.stream.Collectors.toUnmodifiableList;

@RestController
@AllArgsConstructor
public class PlaceController {
    private PlaceService placeService;
    private PlaceMapper placeMapper;

    @GetMapping("/places")
    public List<Place> getPlaces() {
        return placeService
                .getAll()
                .stream()
                .map(placeMapper::toRest)
                .collect(toUnmodifiableList());

    }

    @GetMapping("/places/{id}")
    public Place getPlaceById(@PathVariable("id") String id) {
        return placeMapper.toRest(placeService.getById(id));
    }

    @PutMapping("/places")
    public List<Place> saveOrUpdatePlaces(@RequestBody List<Place> toWrite) {
        var saved = placeService.saveAll(toWrite.stream()
                .map(placeMapper::toDomain)
                .collect(toUnmodifiableList()));
        return saved.stream()
                .map(placeMapper::toRest)
                .collect(toUnmodifiableList());
    }
}
