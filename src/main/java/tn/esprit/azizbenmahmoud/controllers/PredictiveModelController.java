package tn.esprit.azizbenmahmoud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import tn.esprit.azizbenmahmoud.entity.PredictiveModel;
import tn.esprit.azizbenmahmoud.services.ImodelService;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/models")
public class PredictiveModelController {

    @Autowired
    private ImodelService modelService;

    // Create a new predictive model
    @PostMapping()
    public ResponseEntity addModel(@RequestBody PredictiveModel model) {
        PredictiveModel newmodel = modelService.addModel(model);
        return new ResponseEntity(newmodel, HttpStatus.CREATED);
    }


    @PostMapping("/{id}/predict")
    public ResponseEntity<?> predict(@PathVariable Long id, @RequestBody Map<String, Object> inputData) {
        PredictiveModel model = modelService.getModelById(id);
        if (model == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Model not found");
        }

        // Forward to Flask
        String flaskApiUrl = model.getApiEndpoint();
        try {
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<Map> flaskResponse = restTemplate.postForEntity(flaskApiUrl, inputData, Map.class);
            return ResponseEntity.ok(flaskResponse.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error while calling Flask API: " + e.getMessage());
        }
    }


    // Get all predictive models
    @GetMapping
    public ResponseEntity<List<PredictiveModel>> getAllModels() {
        List<PredictiveModel> models = modelService.getAllModels();
        return new ResponseEntity<>(models, HttpStatus.OK);
    }

    // Get predictive model by ID
    @GetMapping("/{id}")
    public ResponseEntity<PredictiveModel> getModelById(@PathVariable Long id) {
        try {
            PredictiveModel model = modelService.getModelById(id);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Update a predictive model
    @PutMapping("/{id}")
    public ResponseEntity<PredictiveModel> updateModel(@PathVariable Long id, @RequestBody PredictiveModel updatedModel) {
        try {
            PredictiveModel model = modelService.updateModel(id, updatedModel);
            return new ResponseEntity<>(model, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Delete a predictive model
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteModel(@PathVariable Long id) {
        try {
            modelService.deleteModel(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
