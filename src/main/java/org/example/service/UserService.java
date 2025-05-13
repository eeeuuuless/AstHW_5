package org.example.service;

import org.example.dto.UserDTO;
import org.example.entity.User;
import org.example.notification.UserNotification;
import org.example.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    private KafkaTemplate<String, UserNotification> kafkaTemplate;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    private UserDTO convertToDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setAge(user.getAge());
        userDTO.setCreatedAt(user.getCreatedAt());
        return userDTO;

    };

    public UserDTO createUser( UserDTO userDTO) {
        User user = new User();
        user.setName(userDTO.getName());
        user.setAge(userDTO.getAge());
        user.setEmail(userDTO.getEmail());

        User savedUser = userRepository.save(user);

        UserNotification userNotification = new UserNotification();
        userNotification.setEmail(savedUser.getEmail());
        userNotification.setOperation("create");
        kafkaTemplate.send("notification", userNotification);

        return convertToDTO(savedUser);
    }

    public UserDTO getUserById(long id) {
        User user = userRepository.findById(id).orElse(null);
        return user != null ? convertToDTO(user) : null;
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserDTO> userDTOs = new ArrayList<>();
        for (User user : users) {
            userDTOs.add(convertToDTO(user));
        }
        return userDTOs;
    }
    public UserDTO updateUser(long id, UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDTO.getName());
            user.setAge(userDTO.getAge());
            user.setEmail(userDTO.getEmail());
            User savedUser = userRepository.save(user);
            return convertToDTO(savedUser);
        }
        return null;
    }
    public void deleteUser(long id) {
            userRepository.deleteById(id);

            UserNotification userNotification = new UserNotification();
            userNotification.setOperation("deleted");
            kafkaTemplate.send("notification", userNotification);
    }
}
