CREATE TABLE IF NOT EXISTS public."PolyHead"
(
    poly_head_id uuid NOT NULL DEFAULT gen_random_uuid(),
    session_id text COLLATE pg_catalog."default" NOT NULL,
    number_of_blocks smallint NOT NULL,
    number_of_polys bigint NOT NULL,
    created_ts timestamp without time zone NOT NULL DEFAULT (now() AT TIME ZONE 'utc'::text),
    CONSTRAINT "PK_POLY_HEAD_ID" PRIMARY KEY (poly_head_id)
)

CREATE TABLE IF NOT EXISTS public."Poly"
(
    poly_id uuid NOT NULL DEFAULT gen_random_uuid(),
    poly_head_id uuid NOT NULL,
    poly json NOT NULL,
    CONSTRAINT "PK_POLY_ID" PRIMARY KEY (poly_id),
    CONSTRAINT "FK_POLY_HEAD_ID" FOREIGN KEY (poly_head_id)
        REFERENCES public."PolyHead" (poly_head_id) MATCH SIMPLE
        ON UPDATE CASCADE
        ON DELETE CASCADE
        NOT VALID
)