--liquibase formatted sql

--changeset ilya:2
ALTER TABLE to_do_data
ADD COLUMN is_completed BOOLEAN DEFAULT FALSE NOT NULL;