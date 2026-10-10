package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.User;
import Igromania.ru.GameClub.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final UserRepository userRepository;

    public ProfileController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping
    public String profile(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/edit")
    public String editProfile(@AuthenticationPrincipal UserDetails userDetails,
                              @RequestParam String email, @RequestParam String phone) {
        User currentUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        currentUser.setEmail(email);
        currentUser.setPhone(phone);
        userRepository.save(currentUser);
        return "redirect:/profile";
    }
}
