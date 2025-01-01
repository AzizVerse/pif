package tn.esprit.azizbenmahmoud.services;

import tn.esprit.azizbenmahmoud.entity.PredictiveModel;

import java.util.List;

public interface ImodelService {
    PredictiveModel addModel(PredictiveModel model); // Add a new model
    List<PredictiveModel> getAllModels(); // Get all models
    PredictiveModel getModelById(Long id); // Get a model by ID
    PredictiveModel updateModel(Long id, PredictiveModel updatedModel); // Update a model
    void deleteModel(Long id);
}
