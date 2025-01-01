package tn.esprit.azizbenmahmoud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.azizbenmahmoud.entity.Claim;


public interface ClaimRepository  extends JpaRepository<Claim, Long> {
}
