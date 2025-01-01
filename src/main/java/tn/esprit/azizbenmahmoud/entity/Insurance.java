package tn.esprit.azizbenmahmoud.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
@Getter
@Setter
@Builder

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Insurance {
    @Getter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idInsurance;
    private LocalDate startDate;
    private LocalDate endDate;

    @Getter
    @Enumerated(EnumType.STRING)
    private IType type;
    private float clientcoverageamount;
    private float clientpremium;
    private Integer duration;
    private Long idorder;
    @Getter
    @Column(columnDefinition = "TEXT")
    private String policy;
    @Enumerated(EnumType.STRING)
    private  InStatus state;
    @ManyToOne
    @JoinColumn(name = "id")
    @JsonBackReference
    private User user;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "insurance")
    @JsonManagedReference
    private Set<Claim> claims;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "insurance")
    private Set<Premium> premiums;

    @OneToMany(cascade = CascadeType.ALL,mappedBy = "insurance")
    private Set<Forecast> Forecasts ;


    @ManyToMany(cascade = CascadeType.ALL)
    @JsonBackReference
    private Set<PredictiveModel> predictiveModels;

}
