package Igromania.ru.GameClub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/play")
public class PlayController {

    @GetMapping("/snake")
    public String snake() {
        return "play/snake";
    }

    @GetMapping("/minesweeper")
    public String minesweeper() {
        return "play/minesweeper";
    }

    // Третья игра — перенаправление на общую страницу крестиков-ноликов
    @GetMapping("/tictactoe")
    public String tictactoeRedirect() {
        return "redirect:/tictactoe";
    }
}
