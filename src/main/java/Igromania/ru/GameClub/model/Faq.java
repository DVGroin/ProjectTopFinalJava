package Igromania.ru.GameClub.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "faq_items")
@Data
public class Faq {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String question;

    @Column(columnDefinition = "TEXT")
    private String answer;
}
