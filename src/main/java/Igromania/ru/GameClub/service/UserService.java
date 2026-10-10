package Igromania.ru.GameClub.service;

import Igromania.ru.GameClub.model.User;
import Igromania.ru.GameClub.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void updateUserProfile(String username, String email, String phone) {
        User user = userRepository.findByUsername(username).orElseThrow();
        user.setEmail(email);
        user.setPhone(phone);
        userRepository.save(user);
    }
}
