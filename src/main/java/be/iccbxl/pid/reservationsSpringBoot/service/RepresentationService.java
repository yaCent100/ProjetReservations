package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Location;
import be.iccbxl.pid.reservationsSpringBoot.model.Representation;
import be.iccbxl.pid.reservationsSpringBoot.model.Show;
import be.iccbxl.pid.reservationsSpringBoot.repository.RepresentationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class RepresentationService {

    @Autowired
    private RepresentationRepository representationRepository;

    public List<Representation> getAll() {
        List<Representation> representations = new ArrayList<>();

        representationRepository.findAll().forEach(representations::add);

        return representations;
    }

    public Representation get(Long id) {
        return representationRepository.findById(id).orElse(null);
    }

    public void add(Representation representation) {
        representationRepository.save(representation);
    }

    public void update(String id, Representation representation) {
        representationRepository.save(representation);
    }

    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);

        representationRepository.deleteById(indice);
    }

    public List<Representation> getFromLocation(Location location) {
        return representationRepository.findByLocation(location);
    }

    public List<Representation> getFromShow(Show show) {
        return representationRepository.findByShow(show);
    }

	public Representation getByWhen(LocalDateTime when) {
	    return representationRepository.findByWhen(when);
	}

}
