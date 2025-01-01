package tn.esprit.azizbenmahmoud.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.azizbenmahmoud.entity.*;
import tn.esprit.azizbenmahmoud.repositories.ClaimRepository;
import tn.esprit.azizbenmahmoud.repositories.InsuranceRepository;
import tn.esprit.azizbenmahmoud.repositories.ReclamationRepository;
import tn.esprit.azizbenmahmoud.repositories.UserRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReclamationImpServ implements IReclamationService{
    @Autowired
    private ReclamationRepository reclamationRepository;
    @Autowired
    private InsuranceRepository insuranceRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ClaimRepository claimRepository;

    @Override
    public Reclamation createReclamation(Long userId, Reclamation reclamation) {
        // Set the current date as the submission date
        reclamation.setDateSubmitted(LocalDate.now());
        reclamation.setStatus(RcStatus.PENDING);

        // Validate the user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        reclamation.setUser(user); // Set the found user

        // If an insurance ID is provided, validate and set the insurance
        if (reclamation.getInsurance() != null && reclamation.getInsurance().getIdInsurance() != null) {
            Insurance insurance = insuranceRepository.findById(reclamation.getInsurance().getIdInsurance())
                    .orElseThrow(() -> new IllegalArgumentException("Insurance not found"));
            reclamation.setInsurance(insurance); // Set the found insurance
        }
        if (reclamation.getClaim() != null && reclamation.getClaim().getIdClaim()!=null) {
            Claim claim = claimRepository.findById(reclamation.getClaim().getIdClaim())
                    .orElseThrow(() -> new IllegalArgumentException("Claim not found"));
            reclamation.setClaim(claim); // Set the found claim
        }

        // Save the reclamation to the database
        return reclamationRepository.save(reclamation);
    }

    @Override
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    @Override
    public Optional<Reclamation> getReclamationById(Long id) {
        return reclamationRepository.findById(id);
    }


    // Helper method to initialize nested objects





    @Override
    public Reclamation updateReclamation(Long id, String status, String resolution) {
        Optional<Reclamation> reclamationOptional = reclamationRepository.findById(id);
        if (reclamationOptional.isPresent()) {
            Reclamation reclamation = reclamationOptional.get();
            reclamation.setStatus(RcStatus.valueOf(status)); // Update the status
            reclamation.setResolution(resolution); // Update the resolution
            reclamation.setDateResolved(LocalDate.now()); // Set the date resolved to current date
            return reclamationRepository.save(reclamation);
        }
        return null; // Or throw an exception if preferred
    }
    @Override
    public List<Reclamation> getReclamationsByUserId(Long userId) {
        // Find the user by userId
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Return the list of reclamations associated with the user
        return user.getReclamations(); // Assuming the User entity has a List<Reclamation> field named "reclamations"
    }

    @Override
    public boolean deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
        return false;
    }
}
