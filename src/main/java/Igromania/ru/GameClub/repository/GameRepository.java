package Igromania.ru.GameClub.repository;

import Igromania.ru.GameClub.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
}
