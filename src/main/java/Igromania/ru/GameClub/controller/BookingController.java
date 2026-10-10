package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import Igromania.ru.GameClub.repository.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/booking")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final GameRepository gameRepository;
    private final GameZoneRepository gameZoneRepository;
    private final UserRepository userRepository;
    private final MenuItemRepository menuItemRepository;

    public BookingController(BookingRepository bookingRepository, GameRepository gameRepository,
                             GameZoneRepository gameZoneRepository, UserRepository userRepository, MenuItemRepository menuItemRepository) {
        this.bookingRepository = bookingRepository;
        this.gameRepository = gameRepository;
        this.gameZoneRepository = gameZoneRepository;
        this.userRepository = userRepository;
        this.menuItemRepository = menuItemRepository;
    }

    @GetMapping("/new")
    public String bookingForm(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("zones", gameZoneRepository.findAll());
        model.addAttribute("menuItems", menuItemRepository.findAll());
        return "booking-form";
    }

    @PostMapping("/create")
    public String createBooking(@AuthenticationPrincipal UserDetails userDetails,
                                @RequestParam String bookingType,
                                @RequestParam(required = false) Long gameId,
                                @RequestParam(required = false) Long gameZoneId,
                                @RequestParam String startTime,
                                @RequestParam int durationHours,
                                @RequestParam(required = false) Boolean isCompanyEvent,
                                @RequestParam(required = false) String companyName,
                                @RequestParam(required = false) Integer guestCount) {

        User user = userRepository.findByUsername(userDetails.getUsername()).orElseThrow();
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setStartTime(LocalDateTime.parse(startTime));
        booking.setDurationHours(durationHours);

        if ("GAME".equals(bookingType) && gameId != null) {
            Game game = gameRepository.findById(gameId).orElseThrow();
            booking.setIdGame(game);
            booking.setGameZone(game.getGameZone());
        } else if (gameZoneId != null) {
            booking.setGameZone(gameZoneRepository.findById(gameZoneId).orElseThrow());
        }

        if (Boolean.TRUE.equals(isCompanyEvent)) {
            GroupEvent groupEvent = new GroupEvent();
            groupEvent.setName(companyName);
            groupEvent.setGuestCount(guestCount);
            booking.setGroupEvent(groupEvent);
        }

        bookingRepository.save(booking);
        return "redirect:/profile";
    }

    @PostMapping("/{id}/cancel")
    public String cancelBooking(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        if (booking.getUser().getUsername().equals(userDetails.getUsername()) && "NEW".equals(booking.getStatus())) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        }
        return "redirect:/profile";
    }
}
