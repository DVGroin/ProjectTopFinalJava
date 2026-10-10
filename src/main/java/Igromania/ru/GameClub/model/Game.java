package Igromania.ru.GameClub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "games")
@Data
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;

    @Column(name = "min_age")
    private int minAge;

    @ManyToOne
    @JoinColumn(name = "game_zone_id")
    private GameZone gameZone;
}
