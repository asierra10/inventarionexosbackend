CREATE DATABASE inventario
    WITH
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'en_US.utf8'
    LC_CTYPE = 'en_US.utf8'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;

CREATE SCHEMA public
       AUTHORIZATION postgres;

COMMENT ON SCHEMA public
       IS 'standard public schema';

GRANT ALL ON SCHEMA public TO PUBLIC;

GRANT ALL ON SCHEMA public TO postgres;

CREATE TABLE public.producto
(
    nombre character varying(100) COLLATE pg_catalog."default" NOT NULL,
    cantidad bigint NOT NULL,
    usuario_creador bigint NOT NULL,
    fecha_creacion date NOT NULL,
    usuario_editor bigint,
    fecha_edicion date,
    id_producto integer NOT NULL DEFAULT nextval('producto_id_producto_seq'::regclass),
    CONSTRAINT producto_pkey PRIMARY KEY (id_producto),
    CONSTRAINT creator FOREIGN KEY (usuario_creador)
        REFERENCES public.usuario (id_usuario) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT editor FOREIGN KEY (usuario_editor)
        REFERENCES public.usuario (id_usuario) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.producto
    OWNER to root;

CREATE TABLE public.usuario
(
    id_usuario integer NOT NULL DEFAULT nextval('usuario_id_usuario_seq'::regclass),
    nombre_usuario character varying(200) COLLATE pg_catalog."default",
    edad bigint,
    fecha_ingreso date,
    cargo bigint,
    CONSTRAINT "Usuario_pkey" PRIMARY KEY (id_usuario),
    CONSTRAINT cargo FOREIGN KEY (cargo)
        REFERENCES public.cargos (id_cargo) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
)

TABLESPACE pg_default;

ALTER TABLE public.usuario
    OWNER to root;

CREATE TABLE public.cargos
(
    cargo character varying COLLATE pg_catalog."default" NOT NULL,
    id_cargo integer NOT NULL DEFAULT nextval('cargos_id_cargo_seq'::regclass),
    CONSTRAINT cargos_pkey PRIMARY KEY (id_cargo)
)

TABLESPACE pg_default;

ALTER TABLE public.cargos
    OWNER to root;

