package Igromania.ru.GameClub.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_zone_id")
    private GameZone gameZone;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game idGame;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "group_event_id")
    private GroupEvent groupEvent;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "duration_hours")
    private int durationHours;

    private String status = "NEW";
}
