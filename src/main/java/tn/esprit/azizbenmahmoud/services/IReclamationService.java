package tn.esprit.azizbenmahmoud.services;

import tn.esprit.azizbenmahmoud.entity.Reclamation;

import java.util.List;
import java.util.Optional;

public interface IReclamationService {
    Reclamation createReclamation(Long userId, Reclamation reclamation);
    List<Reclamation> getAllReclamations();
    Optional<Reclamation> getReclamationById(Long id);
    List<Reclamation> getReclamationsByUserId(Long userId);
    Reclamation updateReclamation(Long id, String status, String resolution);
    boolean deleteReclamation(Long id);
}
