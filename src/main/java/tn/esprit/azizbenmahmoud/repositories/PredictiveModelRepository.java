package tn.esprit.azizbenmahmoud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.azizbenmahmoud.entity.PredictiveModel;

@Repository
public interface PredictiveModelRepository extends JpaRepository<PredictiveModel, Long> {
}
