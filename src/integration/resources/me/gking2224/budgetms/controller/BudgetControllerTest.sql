
insert into model_type (model_type_id, name, enabled) values (100, "Model Type", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (200, 100, 'Test Model');

insert into model_variable (model_variable_id, name, type) values (600, 'existingVar', 'java.lang.String');

