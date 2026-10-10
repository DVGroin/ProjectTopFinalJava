package Igromania.ru.GameClub.repository;

import Igromania.ru.GameClub.model.MenuItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuItemRepository extends JpaRepository<MenuItem, Long> {
}
