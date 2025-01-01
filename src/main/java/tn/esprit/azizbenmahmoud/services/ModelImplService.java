package tn.esprit.azizbenmahmoud.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.azizbenmahmoud.entity.ModelStatus;
import tn.esprit.azizbenmahmoud.entity.PredictiveModel;
import tn.esprit.azizbenmahmoud.repositories.PredictiveModelRepository;

import java.time.LocalDate;
import java.util.*;

@Service
public class ModelImplService implements ImodelService{
    @Autowired
    private PredictiveModelRepository modelRepository;


    @Override
    public PredictiveModel addModel(PredictiveModel model) {
        return  modelRepository.save(model);
    }

    @Override
    public List<PredictiveModel> getAllModels() {
        return modelRepository.findAll(); // Retrieve all models
    }

    @Override
    public PredictiveModel getModelById(Long id) {
        Optional<PredictiveModel> model = modelRepository.findById(id);
        return model.orElseThrow(() -> new RuntimeException("Model not found!"));
    }

    @Override
    public PredictiveModel updateModel(Long id, PredictiveModel updatedModel) {
        PredictiveModel existingModel = getModelById(id); // Retrieve existing model
        existingModel.setName(updatedModel.getName());
        existingModel.setType(updatedModel.getType());
        existingModel.setParameters(updatedModel.getParameters());
        existingModel.setApiEndpoint(updatedModel.getApiEndpoint());
        existingModel.setModelFilePath(updatedModel.getModelFilePath());
        existingModel.setAccuracy(updatedModel.getAccuracy());
        existingModel.setVersion(updatedModel.getVersion());
        existingModel.setStatus(updatedModel.getStatus());
        return modelRepository.save(existingModel); // Update model
    }

    @Override
    public void deleteModel(Long id) {
        modelRepository.deleteById(id); // Delete model by ID
    }
}
