package Igromania.ru.GameClub.repository;

import Igromania.ru.GameClub.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingRepository extends JpaRepository<Booking, Long> {
}
