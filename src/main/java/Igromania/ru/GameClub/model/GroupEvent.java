package Igromania.ru.GameClub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "group_events")
@Data
public class GroupEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "guest_count")
    private int guestCount;
}
