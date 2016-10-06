
insert into budget (budget_id, project_id, name) values (1, 1, 'Test Budget');

insert into role (role_id, budget_id, name, location_id, rate) values (1, 1, 'Test role', 1, 500.00);

insert into role_fte (role_id, month, fte) values (1, 0, 1);
insert into role_fte (role_id, month, fte) values (1, 1, 1);
insert into role_fte (role_id, month, fte) values (1, 2, 1);
insert into role_fte (role_id, month, fte) values (1, 3, 1);
insert into role_fte (role_id, month, fte) values (1, 4, 1);
insert into role_fte (role_id, month, fte) values (1, 5, 1);
insert into role_fte (role_id, month, fte) values (1, 6, 1);
insert into role_fte (role_id, month, fte) values (1, 7, 1);
insert into role_fte (role_id, month, fte) values (1, 8, 1);
insert into role_fte (role_id, month, fte) values (1, 9, 1);
insert into role_fte (role_id, month, fte) values (1, 10, 1);
insert into role_fte (role_id, month, fte) values (1, 11, 1);

insert into role_allocation (role_allocation_id, role_id, resource_id, location_id, rate)
values (1, 1, 1, 1, 400.00);

insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 0, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 1, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 2, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 3, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 4, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 5, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 6, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 7, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 8, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 9, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 10, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'FORECAST', 11, 1);

insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 0, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 1, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 2, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 3, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 4, 1);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 5, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 6, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 7, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 8, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 9, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 10, 0);
insert into role_allocation_fte (role_allocation_id, type, month, fte)
values (1, 'ACTUALS', 11, 0);

