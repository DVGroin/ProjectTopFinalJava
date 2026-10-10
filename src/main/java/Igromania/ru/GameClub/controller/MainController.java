package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.Feedback;
import Igromania.ru.GameClub.repository.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class MainController {

    private final NewsRepository newsRepository;
    private final MenuItemRepository menuItemRepository;
    private final FaqRepository faqRepository;
    private final FeedbackRepository feedbackRepository;
    private final GameZoneRepository gameZoneRepository;

    public MainController(NewsRepository newsRepository, MenuItemRepository menuItemRepository,
                          FaqRepository faqRepository, FeedbackRepository feedbackRepository,
                          GameZoneRepository gameZoneRepository) {
        this.newsRepository = newsRepository;
        this.menuItemRepository = menuItemRepository;
        this.faqRepository = faqRepository;
        this.feedbackRepository = feedbackRepository;
        this.gameZoneRepository = gameZoneRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        return "index";
    }

    @GetMapping("/catalog")
    public String catalog(Model model) {
        model.addAttribute("zones", gameZoneRepository.findAll());
        return "catalog";
    }

    @GetMapping("/menu")
    public String menu(Model model) {
        model.addAttribute("menuItems", menuItemRepository.findAll());
        return "menu";
    }

    @GetMapping("/faq")
    public String faq(Model model) {
        model.addAttribute("faqs", faqRepository.findAll());
        return "faq";
    }

    @GetMapping("/contacts")
    public String contacts(Model model) {
        // Имитируем информацию о клубе из БД
        return "contacts";
    }

    @PostMapping("/contacts/feedback")
    public String sendFeedback(@ModelAttribute Feedback feedback, Model model) {
        feedbackRepository.save(feedback);
        model.addAttribute("successMessage", "Сообщение успешно отправлено администраторам!");
        return "contacts";
    }

    @GetMapping("/tictactoe")
    public String tictactoe() {
        return "tictactoe";
    }
}
