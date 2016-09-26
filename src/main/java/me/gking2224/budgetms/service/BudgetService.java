package me.gking2224.budgetms.service;

import java.util.List;

import me.gking2224.budgetms.jpa.Budget;

public interface BudgetService {

    Budget createBudget(Budget budget);

    List<Budget> findAllBudgets();

    Budget updateBudget(Budget budget);

    void deleteBudget(Long id);

    Budget findBudgetById(Long id);

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
