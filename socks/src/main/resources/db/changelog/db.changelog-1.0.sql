--liquibase formatted sql

--changeset alexmaxpower:1
DROP TABLE IF EXISTS socks;

--changeset alexmaxpower:2
CREATE TABLE IF NOT EXISTS socks
(
    id       BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    color    VARCHAR(255)                            NOT NULL,
    cotton   SMALLINT                                NOT NULL,
    quantity INTEGER                                 NOT NULL,
    CONSTRAINT PK_SOCKS PRIMARY KEY (id)
);