-- Database: PlaneShop

-- DROP DATABASE IF EXISTS "PlaneShop";

CREATE DATABASE "PlaneShop"
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Russian_Russia.1251'
    LC_CTYPE = 'Russian_Russia.1251'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

-- Table: public.Invoices

-- DROP TABLE IF EXISTS public."Invoices";

CREATE TABLE IF NOT EXISTS public."Invoices"
(
    id uuid NOT NULL,
    sum integer NOT NULL,
    created date NOT NULL,
    CONSTRAINT "Invoices_pkey" PRIMARY KEY (id)
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Invoices"
    OWNER to postgres;

-- Table: public.CargoPlanes

-- DROP TABLE IF EXISTS public."CargoPlanes";

CREATE TABLE IF NOT EXISTS public."CargoPlanes"
(
    id uuid NOT NULL,
    brand character varying(40) COLLATE pg_catalog."default" NOT NULL,
    model character varying(40) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    count integer NOT NULL,
    currency character varying(6) COLLATE pg_catalog."default" NOT NULL,
    created date NOT NULL,
    color character varying(40) COLLATE pg_catalog."default" NOT NULL,
    material character varying(40) COLLATE pg_catalog."default" NOT NULL,
    load_capacity integer NOT NULL,
    count_of_crew integer NOT NULL,
    invoice_id uuid,
    CONSTRAINT "CargoPlanes_pkey" PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoices" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."CargoPlanes"
    OWNER to postgres;

-- Table: public.Fighters

-- DROP TABLE IF EXISTS public."Fighters";

CREATE TABLE IF NOT EXISTS public."Fighters"
(
    id uuid NOT NULL,
    brand character varying(40) COLLATE pg_catalog."default" NOT NULL,
    model character varying(40) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    count integer NOT NULL,
    currency character varying(6) COLLATE pg_catalog."default" NOT NULL,
    created date NOT NULL,
    color character varying(40) COLLATE pg_catalog."default" NOT NULL,
    material character varying(40) COLLATE pg_catalog."default" NOT NULL,
    type_of_fighter character varying(40) COLLATE pg_catalog."default" NOT NULL,
    bomb_load integer NOT NULL,
    invoice_id uuid,
    CONSTRAINT "Fighters_pkey" PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoices" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."Fighters"
    OWNER to postgres;

-- Table: public.PassengerPlanes

-- DROP TABLE IF EXISTS public."PassengerPlanes";

CREATE TABLE IF NOT EXISTS public."PassengerPlanes"
(
    id uuid NOT NULL,
    brand character varying(40) COLLATE pg_catalog."default" NOT NULL,
    model character varying(40) COLLATE pg_catalog."default" NOT NULL,
    price integer NOT NULL,
    count integer NOT NULL,
    currency character varying(6) COLLATE pg_catalog."default" NOT NULL,
    created date NOT NULL,
    color character varying(40) COLLATE pg_catalog."default" NOT NULL,
    material character varying(40) COLLATE pg_catalog."default" NOT NULL,
    number_of_passenger integer NOT NULL,
    range_of_flight integer NOT NULL,
    invoice_id uuid,
    CONSTRAINT "PassengerPlanes_pkey" PRIMARY KEY (id),
    CONSTRAINT invoice_id FOREIGN KEY (invoice_id)
        REFERENCES public."Invoices" (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE SET NULL
)

    TABLESPACE pg_default;

ALTER TABLE IF EXISTS public."PassengerPlanes"
    OWNER to postgres;