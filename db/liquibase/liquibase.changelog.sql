--liquibase formatted sql

--changeset author:gking stripComments:true

create table demo_table (
    id int primary key,
    name varchar(255)
    );
--rollback drop table test


--preconditions onFail:HALT onError:HALT
--precondition-sql-check expectedResult:0 SELECT COUNT(*) FROM demo_table