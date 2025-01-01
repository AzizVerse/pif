package tn.esprit.azizbenmahmoud.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;


@Table(name = "predictive_models")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class PredictiveModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_model")
    private Long idModel;

    @Column(name = "model_name", nullable = false, length = 255)
    @JsonProperty("name")
    private String name;

    @Column(name = "model_type", nullable = false, length = 50)
    @JsonProperty("type")
    private String type;

    @Column(name = "creation_date", nullable = false, updatable = false)
    @JsonProperty("creationDate")
    private LocalDate creationDate = LocalDate.now();

    @Column(name = "model_parameters", columnDefinition = "TEXT")
    @JsonProperty("parameters")
    private String parameters;

    @Column(name = "api_endpoint", nullable = false)
    @JsonProperty("apiEndpoint")
    private String apiEndpoint;

    @Column(name = "model_file_path", nullable = false)
    @JsonProperty("modelFilePath")
    private String modelFilePath;

    @Column(name = "model_accuracy")
    @JsonProperty("accuracy")
    private Double accuracy;

    @Column(name = "model_version", nullable = false, length = 20)
    @JsonProperty("version")
    private String version;


    @Column(name = "model_status", nullable = false)
    @JsonProperty("status")
    private ModelStatus status;

    @ManyToMany(mappedBy = "predictiveModels", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Insurance> insurances = new HashSet<>();
}

