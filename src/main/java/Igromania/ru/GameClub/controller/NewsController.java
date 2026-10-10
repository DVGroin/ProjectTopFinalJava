package Igromania.ru.GameClub.controller;

import Igromania.ru.GameClub.model.News;
import Igromania.ru.GameClub.model.User;
import Igromania.ru.GameClub.repository.NewsRepository;
import Igromania.ru.GameClub.repository.UserRepository;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/news")
public class NewsController {

    private final NewsRepository newsRepository;
    private final UserRepository userRepository;

    public NewsController(NewsRepository newsRepository, UserRepository userRepository) {
        this.newsRepository = newsRepository;
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

    // Отображение страницы со списком новостей и формой публикации
    @GetMapping
    public String manageNews(Model model) {
        model.addAttribute("newsList", newsRepository.findAll());
        return "news_management";
    }

    // Создание и публикация новости (Доступно уровням админки 1, 2, 3)
    @PostMapping("/create")
    public String createNews(@RequestParam String title, @RequestParam String type, @RequestParam String content) {
        News news = new News();
        news.setTitle(title);
        news.setType(type);
        news.setContent(content);
        newsRepository.save(news);
        return "redirect:/admin/news";
    }

    // Удаление новости (Доступно уровням админки 1, 2, 3)
    @PostMapping("/{id}/delete")
    public String deleteNews(@PathVariable Long id) {
        newsRepository.deleteById(id);
        return "redirect:/admin/news";
    }
}
