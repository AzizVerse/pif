package tn.esprit.azizbenmahmoud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Claim {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idClaim;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private CStatus status;
    private String amount;
    private String documentURL;
    @Getter
    @Column(columnDefinition = "TEXT")
    private String details;

    @ManyToOne
    @JoinColumn(name = "insurance_id")
    @JsonBackReference
    private Insurance insurance;
}
