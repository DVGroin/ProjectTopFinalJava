package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.*;
import Igromania.ru.GameClub.repository.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final GameZoneRepository gameZoneRepository;

    public AdminController(BookingRepository bookingRepository, UserRepository userRepository,
                           GameZoneRepository gameZoneRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.gameZoneRepository = gameZoneRepository;
    }

    // Доступно всем трем администраторам (Lvl 1, 2, 3)
    @ModelAttribute
    public void addAdminContext(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            model.addAttribute("currentUser", user);
        }
    }

    // Главный пульт: просмотр всех заявок на бронирование игроками
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        model.addAttribute("bookings", bookingRepository.findAll());
        model.addAttribute("statuses", new String[]{"NEW", "IN_PROGRESS", "COMPLETED", "CANCELLED"});
        return "admin/dashboard";
    }

    // Изменение статуса брони (Обрабатывается на пульте)
    @PostMapping("/bookings/{id}/status")
    public String changeBookingStatus(@PathVariable Long id, @RequestParam String status) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(status);
        bookingRepository.save(booking);
        return "redirect:/admin/dashboard";
    }

    /* ==========================================================================
       УРОВЕНЬ 1 и 2: Управление техническими Игровыми зонами и Пользователями
       ========================================================================== */
    @GetMapping("/zones")
    @PreAuthorize("authentication.principal.user.adminLevel <= 2")
    public String manageZones(Model model) {
        model.addAttribute("zones", gameZoneRepository.findAll());
        return "admin/zones_management";
    }

    @PostMapping("/zones/create")
    @PreAuthorize("authentication.principal.user.adminLevel <= 2")
    public String createZone(@RequestParam String name, @RequestParam double pricePerHour, @RequestParam String specifications) {
        GameZone zone = new GameZone();
        zone.setName(name);
        zone.setPricePerHour(pricePerHour);
        zone.setSpecifications(specifications);
        gameZoneRepository.save(zone);
        return "redirect:/admin/zones";
    }

    @PostMapping("/zones/{id}/delete")
    @PreAuthorize("authentication.principal.user.adminLevel <= 2")
    public String deleteZone(@PathVariable Long id) {
        gameZoneRepository.deleteById(id);
        return "redirect:/admin/zones";
    }

    @GetMapping("/users")
    @PreAuthorize("authentication.principal.user.adminLevel <= 2")
    public String manageUsers(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "admin/users_management";
    }

    /* ==========================================================================
       УРОВЕНЬ 1 (Супер-админ): Изменение рангов других администраторов клуба
       ========================================================================== */
    @PostMapping("/users/{id}/level")
    @PreAuthorize("authentication.principal.user.adminLevel == 1")
    public String changeAdminLevel(@PathVariable Long id, @RequestParam int adminLevel) {
        User targetUser = userRepository.findById(id).orElseThrow();
        targetUser.setAdminLevel(adminLevel);
        if (adminLevel > 0) {
            targetUser.setRole("ROLE_ADMIN");
        } else {
            targetUser.setRole("ROLE_USER");
        }
        userRepository.save(targetUser);
        return "redirect:/admin/users";
    }
}
