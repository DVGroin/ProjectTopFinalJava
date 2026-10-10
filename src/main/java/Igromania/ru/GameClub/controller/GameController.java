package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.Game;
import Igromania.ru.GameClub.model.User;
import Igromania.ru.GameClub.repository.GameRepository;
import Igromania.ru.GameClub.repository.GameZoneRepository;
import Igromania.ru.GameClub.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/games")
public class GameController {

    private final GameRepository gameRepository;
    private final GameZoneRepository gameZoneRepository;
    private final UserRepository userRepository;

    public GameController(GameRepository gameRepository, GameZoneRepository gameZoneRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.gameZoneRepository = gameZoneRepository;
        this.userRepository = userRepository;
    }

    // Прокидываем контекст текущего админа на страницу управления
    @ModelAttribute
    public void addAdminContext(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        if (userDetails != null) {
            User user = userRepository.findByUsername(userDetails.getUsername()).orElse(null);
            model.addAttribute("currentUser", user);
        }
    }

    // Отображение страницы со списком игр и формой добавления
    @GetMapping
    public String manageGames(Model model) {
        model.addAttribute("games", gameRepository.findAll());
        model.addAttribute("zones", gameZoneRepository.findAll());
        return "admin/games_management";
    }

    // Создание новой игры (Доступно уровням админки 1, 2, 3)
    @PostMapping("/create")
    public String createGame(@RequestParam String title, @RequestParam String genre,
                             @RequestParam int minAge, @RequestParam Long gameZoneId) {
        Game game = new Game();
        game.setTitle(title);
        game.setGenre(genre);
        game.setMinAge(minAge);
        game.setGameZone(gameZoneRepository.findById(gameZoneId).orElseThrow());
        gameRepository.save(game);
        return "redirect:/admin/games";
    }

    // Удаление игры из каталога (Доступно уровням админки 1, 2, 3)
    @PostMapping("/{id}/delete")
    public String deleteGame(@PathVariable Long id) {
        gameRepository.deleteById(id);
        return "redirect:/admin/games";
    }
}
