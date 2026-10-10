package Igromania.ru.GameClub.service;

import Igromania.ru.GameClub.model.Booking;
import Igromania.ru.GameClub.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    public void saveBooking(Booking booking) {
        bookingRepository.save(booking);
    }

    public void cancelBooking(Long id, String username) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        if (booking.getUser().getUsername().equals(username) && "NEW".equals(booking.getStatus())) {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
        }
    }

    public void updateStatus(Long id, String status) {
        Booking booking = bookingRepository.findById(id).orElseThrow();
        booking.setStatus(status);
        bookingRepository.save(booking);
    }
}
