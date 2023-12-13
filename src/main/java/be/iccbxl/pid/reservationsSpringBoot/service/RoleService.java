package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.Role;
import be.iccbxl.pid.reservationsSpringBoot.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        List<Role> roles = new ArrayList<>();
        roleRepository.findAll().forEach(roles::add);
        return roles;
    }
    
    public Role get(String id) {
        Long indice = (long) Integer.parseInt(id);
        Optional<Role> role = roleRepository.findById(indice);
        return role.orElse(null);
    }
    
    public void add(Role role) {
        roleRepository.save(role);
    }
    public void update(String id, Role role) {
        roleRepository.save(role);
    }

    public void delete(String id){
        Long indice = (long) Integer.parseInt(id);
        roleRepository.deleteById(indice);

    }
    
    public Role findByRole(String roleName) {
        return roleRepository.findByRole(roleName);
    }

}
