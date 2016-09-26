
insert into model_type (model_type_id, name, enabled) values (100, "Type 1", 1);

insert into model_manifest (model_manifest_id, model_type_id, name) values (200, 100, "Test Model 1.1");

insert into model (model_id, model_manifest_id, major_version, minor_version, enabled) values (300, 200, 1, 3, 1);



insert into model_variable (model_variable_id, type, name) values (400, 'java.lang.String', 'countryCode');

insert into model_assigned_variable (model_variable_id, model_id, direction, mandatory) values (400, 300, 'B', 1);
