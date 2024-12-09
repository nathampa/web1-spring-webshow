
CREATE TABLE public.per_perfil (
	per_nr_id serial4 NOT NULL,
	per_tx_nome varchar(200) NOT NULL,
	per_tx_status bpchar(1) DEFAULT 'A'::bpchar NOT NULL,
	CONSTRAINT per_perfil_pk PRIMARY KEY (per_nr_id)
);