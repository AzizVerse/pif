package tn.esprit.azizbenmahmoud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.azizbenmahmoud.entity.Insurance;
@Repository

public interface InsuranceRepository extends JpaRepository<Insurance, Long> {
}
