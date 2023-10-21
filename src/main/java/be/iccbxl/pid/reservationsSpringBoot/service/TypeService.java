package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Type;
import be.iccbxl.pid.reservationsSpringBoot.repository.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TypeService {

    @Autowired
    private TypeRepository typeRepository;

    public List<Type> getAll() {
        List<Type> types = new ArrayList<>();
        typeRepository.findAll().forEach(types::add); return types;
    }

    public Type getType(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Type> type = typeRepository.findById(indice);
        return type.orElse(null);
    }


    public void addType(Type type) {
        typeRepository.save(type);
    }
    public void updateType(String id, Type type) {
        typeRepository.save(type);
    }
    public void deleteType(String id) {
        Long indice = (long) Integer.parseInt(id); typeRepository.deleteById(indice);
    }
}
