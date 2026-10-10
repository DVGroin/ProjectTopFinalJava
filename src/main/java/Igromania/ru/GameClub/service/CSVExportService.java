package Igromania.ru.GameClub.service;

import Igromania.ru.GameClub.model.Booking;
import Igromania.ru.GameClub.repository.BookingRepository;
import org.springframework.stereotype.Service;
import java.io.StringWriter;
import java.util.List;

@Service
public class CSVExportService {

    private final BookingRepository bookingRepository;

    public CSVExportService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public String exportBookingsToCSV() {
        List<Booking> bookings = bookingRepository.findAll();
        StringWriter writer = new StringWriter();

        writer.append("ID,Username,Object,Time,Duration,Status\n");

        for (Booking b : bookings) {
            String target = b.getIdGame() != null ? "Game: " + b.getIdGame().getTitle() : "Zone: " + b.getGameZone().getName();
            writer.append(b.getId().toString()).append(",")
                    .append(b.getUser().getUsername()).append(",")
                    .append(target).append(",")
                    .append(b.getStartTime().toString()).append(",")
                    .append(String.valueOf(b.getDurationHours())).append(",")
                    .append(b.getStatus()).append("\n");
        }

        return writer.toString();
    }
}
