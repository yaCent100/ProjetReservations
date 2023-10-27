package be.iccbxl.pid.reservationsSpringBoot.service;

import be.iccbxl.pid.reservationsSpringBoot.model.User;
import be.iccbxl.pid.reservationsSpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        userRepository.findAll().forEach(users::add);

        return users;
    }

    public User getUser(String id) {
        int indice = Integer.parseInt(id);

        return userRepository.findById(indice);
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(String id, User user) {
        userRepository.save(user);
    }

    public void deleteUser(String id) {
        Long indice = (long) Integer.parseInt(id);

        userRepository.deleteById(indice);
    }
    
    public User finByLogin(String login) {
    	
    	return userRepository.findByLogin(login);
    }


}
