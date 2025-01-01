package tn.esprit.azizbenmahmoud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.azizbenmahmoud.entity.Reclamation;
import tn.esprit.azizbenmahmoud.services.IReclamationService;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/reclamations")
public class ReclamationController {
    @Autowired
    private IReclamationService reclamationService;

    // Create a new reclamation
    @PostMapping
    public ResponseEntity<Reclamation> addReclamation(@RequestParam Long userId, @RequestBody Reclamation reclamation) {
        Reclamation newReclamation = reclamationService.createReclamation(userId, reclamation);
        return new ResponseEntity<>(newReclamation, HttpStatus.CREATED);
    }
    @GetMapping("/by/{userId}")
    public ResponseEntity<List<Reclamation>> getReclamationsByUserId(@PathVariable Long userId) {
        List<Reclamation> reclamations = reclamationService.getReclamationsByUserId(userId);
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    // Get all reclamations
    @GetMapping
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        List<Reclamation> reclamations = reclamationService.getAllReclamations();
        return new ResponseEntity<>(reclamations, HttpStatus.OK);
    }

    // Get reclamation by ID
    @GetMapping("/{id}")
    public ResponseEntity<Reclamation> getReclamationById(@PathVariable Long id) {
        Optional<Reclamation> reclamationOptional = reclamationService.getReclamationById(id);
        if (reclamationOptional.isPresent()) {
            return new ResponseEntity<>(reclamationOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Update reclamation
    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Long id, @RequestBody Map<String, String> updates) {
        String status = updates.get("status");
        String resolution = updates.get("resolution");

        Reclamation updatedReclamation = reclamationService.updateReclamation(id, status, resolution);
        if (updatedReclamation != null) {
            return new ResponseEntity<>(updatedReclamation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // Delete reclamation
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        if (reclamationService.deleteReclamation(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
