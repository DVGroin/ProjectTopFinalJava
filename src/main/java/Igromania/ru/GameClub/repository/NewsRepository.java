package Igromania.ru.GameClub.repository;

import Igromania.ru.GameClub.model.News;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NewsRepository extends JpaRepository<News, Long> {
}
