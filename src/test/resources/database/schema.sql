-- noinspection SqlNoDataSourceInspectionForFile

-- CREATE DATABASE tdinterest WITH ENCODING = 'UTF8' LC_COLLATE = 'en_US.utf8' LC_CTYPE = 'en_US.utf8' TABLESPACE = pg_default CONNECTION LIMIT = - 1;
CREATE TABLE public.projects
(
    pid   bigint                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999999 CACHE 1 ),
    url   text COLLATE pg_catalog."default" NOT NULL,
    owner text COLLATE pg_catalog."default",
    repo  text COLLATE pg_catalog."default",
    CONSTRAINT projects_pkey PRIMARY KEY (pid),
    CONSTRAINT owner_repo_constraint UNIQUE (owner, repo),
    CONSTRAINT projects_url_key UNIQUE (url)
) TABLESPACE pg_default;

CREATE INDEX repo_owner_index
    ON public.projects USING btree (repo COLLATE pg_catalog."default" ASC NULLS LAST,
                                    owner COLLATE pg_catalog."default" ASC NULLS LAST) TABLESPACE pg_default;

CREATE INDEX url_index
    ON public.projects USING btree (url COLLATE pg_catalog."default" ASC NULLS LAST) TABLESPACE pg_default;

CREATE TABLE public.metrics
(
    classes_num          integer,
    complexity           numeric,
    dac                  integer,
    dit                  integer,
    fid                  bigint                            NOT NULL,
    interest_eu          numeric                           NOT NULL,
    lcom                 numeric,
    mid                  bigint                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999999 CACHE 1 ),
    mpc                  numeric,
    nocc                 integer,
    old_size1            integer,
    pid                  bigint                            NOT NULL,
    rfc                  numeric,
    sha                  text COLLATE pg_catalog."default" NOT NULL,
    size1                integer,
    size2                integer,
    wmc                  numeric,
    nom                  numeric,
    kappa                numeric,
    revision_count       bigint                            NOT NULL,
    interest_in_hours    numeric,
    avg_interest_per_loc numeric,
    interest_in_avg_loc  numeric,
    sum_interest_per_loc numeric,
    cbo                  numeric,
    CONSTRAINT metrics_pkey PRIMARY KEY (mid),
    CONSTRAINT pid FOREIGN KEY (pid) REFERENCES public.projects (pid) MATCH SIMPLE
        ON
            UPDATE
            NO ACTION
        ON
            DELETE
            NO ACTION
) TABLESPACE pg_default;

CREATE INDEX pid_index
    ON public.metrics USING hash (pid) TABLESPACE pg_default;

CREATE INDEX rev_count_index
    ON public.metrics USING btree (revision_count ASC NULLS LAST) TABLESPACE pg_default;

ALTER TABLE public.metrics
    CLUSTER
        ON rev_count_index;

CREATE INDEX sha_index
    ON public.metrics USING hash (sha COLLATE pg_catalog."default") TABLESPACE pg_default;

CREATE TABLE public.files
(
    fid         bigint                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999999 CACHE 1 ),
    pid         bigint                            NOT NULL,
    file_path   text COLLATE pg_catalog."default" NOT NULL,
    class_names text[] COLLATE pg_catalog."default",
    sha         text COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT file_pkey PRIMARY KEY (fid),
    CONSTRAINT pid_file_path_sha_unique UNIQUE (pid, file_path, sha),
    CONSTRAINT pid FOREIGN KEY (pid) REFERENCES public.projects (pid) MATCH SIMPLE
        ON
            UPDATE
            NO ACTION
        ON
            DELETE
            NO ACTION
) TABLESPACE pg_default;

CREATE INDEX file_path_sha_index
    ON public.files USING btree (file_path COLLATE pg_catalog."default" ASC NULLS LAST,
                                 sha COLLATE pg_catalog."default" ASC NULLS LAST) TABLESPACE pg_default;

CREATE TABLE public.c_loc
(
    lid            bigint                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999999 CACHE 1 ),
    pid            bigint                            NOT NULL,
    sha            text COLLATE pg_catalog."default" NOT NULL,
    revision_count bigint                            NOT NULL,
    c_loc_val      bigint,
    CONSTRAINT c_loc_pkey PRIMARY KEY (lid),
    CONSTRAINT pid_sha_unique_loc_constr UNIQUE (pid, sha),
    CONSTRAINT pid FOREIGN KEY (pid) REFERENCES public.projects (pid) MATCH SIMPLE
        ON
            UPDATE
            NO ACTION
        ON
            DELETE
            NO ACTION
) TABLESPACE pg_default;

CREATE TABLE public.c_interest
(
    tid                    bigint                            NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 99999999 CACHE 1 ),
    pid                    bigint                            NOT NULL,
    sha                    text COLLATE pg_catalog."default" NOT NULL,
    revision_count         bigint                            NOT NULL,
    c_interest_eu          numeric,
    c_interest_in_hours    numeric,
    c_avg_interest_per_loc numeric,
    c_interest_in_avg_loc  numeric,
    c_sum_interest_per_loc numeric,
    CONSTRAINT c_interest_pkey PRIMARY KEY (tid),
    CONSTRAINT pid_sha_unique_constr UNIQUE (pid, sha),
    CONSTRAINT pid FOREIGN KEY (pid) REFERENCES public.projects (pid) MATCH SIMPLE
        ON
            UPDATE
            NO ACTION
        ON
            DELETE
            NO ACTION
) TABLESPACE pg_default;

CREATE FUNCTION public.update_cumulative_interest() RETURNS trigger
    LANGUAGE PLPGSQL
    COST 100 VOLATILE NOT LEAKPROOF AS
'
BEGIN
    INSERT INTO c_interest (pid, sha, revision_count,
                            c_interest_eu,
                            c_interest_in_hours,
                            c_avg_interest_per_loc,
                            c_interest_in_avg_loc,
                            c_sum_interest_per_loc)
    VALUES (NEW.pid,
            NEW.sha,
            NEW.revision_count,
            NEW.interest_eu,
            NEW.interest_in_hours,
            NEW.avg_interest_per_loc,
            NEW.interest_in_avg_loc,
            NEW.sum_interest_per_loc)
    ON CONFLICT (pid, sha) DO UPDATE
        SET c_interest_eu          = c_interest.c_interest_eu + NEW.interest_eu,
            c_interest_in_hours    = c_interest.c_interest_in_hours + NEW.interest_in_hours,
            c_avg_interest_per_loc = c_interest.c_avg_interest_per_loc + NEW.avg_interest_per_loc,
            c_interest_in_avg_loc  = c_interest.c_interest_in_avg_loc + NEW.interest_in_avg_loc,
            c_sum_interest_per_loc = c_interest.c_sum_interest_per_loc + NEW.sum_interest_per_loc;
    RETURN NEW;
END;
';

CREATE TRIGGER save_c_interest
    AFTER INSERT
    ON public.metrics
    FOR EACH ROW
EXECUTE PROCEDURE public.update_cumulative_interest();

CREATE FUNCTION public.update_cumulative_loc() RETURNS trigger
    LANGUAGE PLPGSQL
    COST 100 VOLATILE NOT LEAKPROOF AS
'
BEGIN
    INSERT INTO c_loc(pid, sha, revision_count, c_loc_val)
    VALUES (NEW.pid, NEW.sha, NEW.revision_count, NEW.size1)
    ON CONFLICT (pid, sha) DO UPDATE
        SET c_loc_val = c_loc.c_loc_val + NEW.size1;
    RETURN NEW;
END;
';

CREATE TRIGGER update_cumulative_loc
    AFTER INSERT
    ON public.metrics
    FOR EACH ROW
EXECUTE PROCEDURE public.update_cumulative_loc();
