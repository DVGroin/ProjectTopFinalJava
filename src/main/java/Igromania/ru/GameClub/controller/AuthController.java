package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.User;
import Igromania.ru.GameClub.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class AuthController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("userDto", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute("userDto") User user, BindingResult result) {
        if (userRepository.existsByUsername(user.getUsername())) {
            result.rejectValue("username", "error.user", "Этот логин уже занят!");
        }
        if (result.hasErrors()) {
            return "register";
        }

        // Хешируем пароль через BCrypt перед записью в H2
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");
        user.setAdminLevel(0);
        userRepository.save(user);

        return "redirect:/login?success=true";
    }
}
