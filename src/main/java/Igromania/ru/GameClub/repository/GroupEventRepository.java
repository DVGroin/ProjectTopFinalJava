package Igromania.ru.GameClub.repository;

import Igromania.ru.GameClub.model.GroupEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupEventRepository extends JpaRepository<GroupEvent, Long> {
}
