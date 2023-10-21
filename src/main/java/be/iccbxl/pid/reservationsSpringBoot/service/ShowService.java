package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Location;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {
    @Autowired
    private ShowRepository showRepository;

    public List<Show> getAll() {
        List<Show> shows = new ArrayList<>();

        showRepository.findAll().forEach(shows::add);

        return shows;
    }

    public Show get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Show> show = showRepository.findById(indice);

        return show.orElse(null);
    }

    public void add(Show show) {
        showRepository.save(show);
    }

    public void update(String id, Show show) {
        showRepository.save(show);
    }

    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);

        showRepository.deleteById(indice);
    }

    public List<Show> getFromLocation(Location location) {
        return showRepository.findByLocation(location);
    }

}
