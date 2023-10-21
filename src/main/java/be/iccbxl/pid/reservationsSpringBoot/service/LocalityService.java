package be.iccbxl.pid.reservationsSpringBoot.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import be.iccbxl.pid.reservationsSpringBoot.model.Locality;
import be.iccbxl.pid.reservationsSpringBoot.repository.LocalityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalityService {

    @Autowired
    private LocalityRepository localityRepository;

    public List<Locality> getAll() {
        List<Locality> localities = new ArrayList<>();
        localityRepository.findAll().forEach(localities::add); return localities;
    }
    public Locality get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Locality> locality = localityRepository.findById(indice);
        return locality.orElse(null);
    }
    public void add(Locality locality) {
        localityRepository.save(locality);
    }
    public void update(String id, Locality locality) {
        localityRepository.save(locality);
    }
    public void delete(String id) {
        Long indice = (long) Integer.parseInt(id);
        localityRepository.deleteById(indice);
    }
}
