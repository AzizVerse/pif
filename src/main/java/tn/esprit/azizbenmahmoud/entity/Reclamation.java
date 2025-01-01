package tn.esprit.azizbenmahmoud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private User user;
    @ManyToOne(optional = true) // Optional relationship with Insurance
    @JoinColumn(name = "insurance_id")
    @JsonManagedReference
    private Insurance insurance;

    @ManyToOne(optional = true) // Optional relationship with Claim
    @JoinColumn(name = "claim_id")
    @JsonManagedReference
    private Claim claim;

    @Column(name = "date_submitted", nullable = false)
    private LocalDate dateSubmitted;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RcStatus status = RcStatus.PENDING;

    @Column(columnDefinition = "TEXT")
    private String resolution;

    private LocalDate dateResolved;

    @Column(name = "reclamation_type", nullable = false)
    private String reclamationType;
}
