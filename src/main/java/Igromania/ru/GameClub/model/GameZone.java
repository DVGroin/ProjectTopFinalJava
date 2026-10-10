package Igromania.ru.GameClub.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import java.util.List;

@Entity
@Table(name = "game_zones")
@Data
public class GameZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "price_per_hour")
    private double pricePerHour;

    @Column(columnDefinition = "TEXT")
    private String specifications;

    @OneToMany(mappedBy = "gameZone", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Game> games;
}
