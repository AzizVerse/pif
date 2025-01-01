package tn.esprit.azizbenmahmoud.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Premium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idPremium;
    private LocalDate date;
    private Float amount;
    private boolean status;


    @ManyToOne
    Insurance insurance;
}
