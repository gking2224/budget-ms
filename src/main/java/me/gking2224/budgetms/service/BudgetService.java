package me.gking2224.budgetms.service;

import java.util.List;

import me.gking2224.budgetms.model.Budget;

public interface BudgetService {

    Budget create(Budget budget);

    List<Budget> findAllBudgets();

    Budget update(Budget budget);

    void delete(Long id);

    Budget findBudgetById(Long id);

    List<Budget> findByProjectId(Long projectId);

    List<Budget> findByResourceId(Long resourceId);

//    ModelManifest createModelManifest(ModelManifest manifest);
//
//    List<ModelType> findAllModelTypes();
//
//    List<ModelManifest> findModelManifestByType(String typeName);
//
//    Model getModelByTypeAndNameAndVersion(String typeName, String name, Version version);
//
//    ModelType createModelType(ModelType type);
//
//    ModelType updateModelType(ModelType type);
//
//    void deleteModelType(Long id);
//
//    ModelType findTypeById(Long id);
//
//    ModelManifest updateModelManifest(ModelManifest manifest);
//
//    void deleteModelManifest(Long id);
//
//    ModelManifest findManifestById(Long id);
//
//    Model getModelById(Long id);
//
//    Model createNewModel(Model model, IncrementType incrementType);
//
//    List<Model> getModelsByManifest(Long manifestId);
//
//    Model updateModel(Model model);

}
