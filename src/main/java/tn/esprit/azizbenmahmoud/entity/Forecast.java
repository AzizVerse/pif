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
public class Forecast {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idForecast;
    private String type;
    private String premiummargin;
    private Double estimated_Compensation_Amount;
    private Double claimProbability;
    private LocalDate date;
    /* un role pour admin aet user */

    @ManyToOne
    Insurance insurance;
}
