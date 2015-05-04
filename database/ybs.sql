--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-05-04 22:07:09

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 186 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2081 (class 0 OID 0)
-- Dependencies: 186
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 177 (class 1259 OID 24995)
-- Name: customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customers (
    id integer NOT NULL,
    nama_perusahaan character varying,
    contact_person character varying,
    alamat character varying,
    telepon character varying,
    email character varying,
    jenis_customer character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);


--
-- TOC entry 176 (class 1259 OID 24993)
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2082 (class 0 OID 0)
-- Dependencies: 176
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customers_id_seq OWNED BY customers.id;


--
-- TOC entry 179 (class 1259 OID 25006)
-- Name: filling; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE filling (
    id integer NOT NULL,
    warna character varying,
    huruf character varying,
    digit integer,
    nomor_terakhir character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);


--
-- TOC entry 178 (class 1259 OID 25004)
-- Name: filling_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE filling_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2083 (class 0 OID 0)
-- Dependencies: 178
-- Name: filling_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE filling_id_seq OWNED BY filling.id;


--
-- TOC entry 185 (class 1259 OID 41379)
-- Name: filling_validasi_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE filling_validasi_rules (
    id bigint NOT NULL,
    validasi_rules_id integer,
    filling_id character varying
);


--
-- TOC entry 184 (class 1259 OID 41377)
-- Name: filling_validasi_rules_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE filling_validasi_rules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2084 (class 0 OID 0)
-- Dependencies: 184
-- Name: filling_validasi_rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE filling_validasi_rules_id_seq OWNED BY filling_validasi_rules.id;


--
-- TOC entry 183 (class 1259 OID 33198)
-- Name: joborder; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE joborder (
    id bigint NOT NULL,
    kode character varying,
    customer integer,
    exportir integer,
    jenis_kegiatan character varying,
    tgl_pelaksanaan date,
    catatan character varying,
    created_at timestamp with time zone,
    created_by integer,
    updated_at timestamp with time zone,
    updated_by integer
);


--
-- TOC entry 182 (class 1259 OID 33196)
-- Name: joborder_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE joborder_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2085 (class 0 OID 0)
-- Dependencies: 182
-- Name: joborder_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE joborder_id_seq OWNED BY joborder.id;


--
-- TOC entry 175 (class 1259 OID 24984)
-- Name: pegawai; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pegawai (
    id integer NOT NULL,
    nama character varying,
    alamat character varying,
    telepon character varying,
    divisi character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone
);


--
-- TOC entry 174 (class 1259 OID 24982)
-- Name: pegawai_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pegawai_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2086 (class 0 OID 0)
-- Dependencies: 174
-- Name: pegawai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE pegawai_id_seq OWNED BY pegawai.id;


--
-- TOC entry 173 (class 1259 OID 24971)
-- Name: users; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users (
    id integer NOT NULL,
    name character varying,
    password character varying,
    divisi character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    remember_token character varying,
    password_seen character varying,
    last_login timestamp with time zone
);


--
-- TOC entry 172 (class 1259 OID 24969)
-- Name: tb_login_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE tb_login_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2087 (class 0 OID 0)
-- Dependencies: 172
-- Name: tb_login_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE tb_login_id_seq OWNED BY users.id;


--
-- TOC entry 181 (class 1259 OID 33187)
-- Name: validasi_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE validasi_rules (
    id integer NOT NULL,
    aturan character varying,
    urutan integer,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true
);


--
-- TOC entry 180 (class 1259 OID 33185)
-- Name: validasi_rules_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE validasi_rules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 180
-- Name: validasi_rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE validasi_rules_id_seq OWNED BY validasi_rules.id;


--
-- TOC entry 1926 (class 2604 OID 24998)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- TOC entry 1927 (class 2604 OID 25009)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling ALTER COLUMN id SET DEFAULT nextval('filling_id_seq'::regclass);


--
-- TOC entry 1931 (class 2604 OID 41382)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling_validasi_rules ALTER COLUMN id SET DEFAULT nextval('filling_validasi_rules_id_seq'::regclass);


--
-- TOC entry 1930 (class 2604 OID 33201)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder ALTER COLUMN id SET DEFAULT nextval('joborder_id_seq'::regclass);


--
-- TOC entry 1925 (class 2604 OID 24987)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai ALTER COLUMN id SET DEFAULT nextval('pegawai_id_seq'::regclass);


--
-- TOC entry 1924 (class 2604 OID 24974)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('tb_login_id_seq'::regclass);


--
-- TOC entry 1928 (class 2604 OID 33190)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules ALTER COLUMN id SET DEFAULT nextval('validasi_rules_id_seq'::regclass);


--
-- TOC entry 2066 (class 0 OID 24995)
-- Dependencies: 177
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at) VALUES (4, 'pt rihamdan', 'milka', 'jl. limbad', '334344', 'silhan@yo.net', 'Exportir', '2015-05-01 05:07:15+07', '2015-05-01 05:07:15+07');
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at) VALUES (5, 'pt. Zuika', 'milham', 'jl. milkomin', '3445546', 'filfa@yo.net', 'Rekanan', '2015-05-01 05:08:04+07', '2015-05-01 05:08:23+07');


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 176
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customers_id_seq', 5, true);


--
-- TOC entry 2068 (class 0 OID 25006)
-- Dependencies: 179
-- Data for Name: filling; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (3, 'merah', 'BNM', 7, NULL, '2015-05-01 09:42:08+07', '2015-05-01 09:42:08+07');
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (1, 'kuning', 'ABgh', 6, NULL, '2015-05-01 09:40:51+07', '2015-05-01 09:43:03+07');
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (6, 'tttt', 'ttt', 3, NULL, '2015-05-02 12:58:38+07', '2015-05-02 12:58:38+07');
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (4, 'jingg', 'ty', 3, NULL, '2015-05-02 12:25:34+07', '2015-05-03 05:09:13+07');
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (5, 'biru', 'kb', 4, NULL, '2015-05-02 12:47:41+07', '2015-05-03 05:09:21+07');
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at) VALUES (7, 'coklat', 'MN', 4, NULL, '2015-05-03 05:16:11+07', '2015-05-03 05:16:11+07');


--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 178
-- Name: filling_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('filling_id_seq', 7, true);


--
-- TOC entry 2074 (class 0 OID 41379)
-- Dependencies: 185
-- Data for Name: filling_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (19, 5, '1');
INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (20, 2, '1');
INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (23, 4, '4');
INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (24, 4, '5');
INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (25, 2, '7');
INSERT INTO filling_validasi_rules (id, validasi_rules_id, filling_id) VALUES (26, 5, '7');


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 184
-- Name: filling_validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('filling_validasi_rules_id_seq', 26, true);


--
-- TOC entry 2072 (class 0 OID 33198)
-- Dependencies: 183
-- Data for Name: joborder; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 182
-- Name: joborder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_id_seq', 1, false);


--
-- TOC entry 2064 (class 0 OID 24984)
-- Dependencies: 175
-- Data for Name: pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at) VALUES (3, 'Deffina', 'Jl. moria dina', '67566', 'Keuangan', '2015-05-01 03:47:40+07', '2015-05-01 03:55:32+07');
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at) VALUES (1, 'Lumbin', 'Jl. kusuma bangsa', '5645', 'IT', '2015-05-01 03:33:34+07', '2015-05-01 04:18:35+07');
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at) VALUES (4, 'Morna', '4545', 'Jl. lisonim', 'Berkas', '2015-05-01 04:18:55+07', '2015-05-01 04:19:02+07');


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 174
-- Name: pegawai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pegawai_id_seq', 4, true);


--
-- TOC entry 2094 (class 0 OID 0)
-- Dependencies: 172
-- Name: tb_login_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('tb_login_id_seq', 13, true);


--
-- TOC entry 2062 (class 0 OID 24971)
-- Dependencies: 173
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login) VALUES (1, 'ade', '$2y$10$xMPQDRbEVwF3KwCRsVjs1.xUF55yRyYky5mzWNOPSL6wilwvV7m9S', 'Admin', '2015-04-28 19:55:36.309+07', '2015-05-03 19:30:11+07', 'RKcubA1BKHdc4Glc1PL3pPusxY34qfeeAkAl8EqwDodrwzxnvl3lvgn4cMAq', NULL, '2015-05-03 19:30:11+07');
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login) VALUES (11, 'milda', '$2y$10$qwu1/HNZg1v56UF3z7.y6eTtQZKjatrxjWLr6qszfg9LGQ/A3Jcke', 'Finance', '2015-04-30 13:49:05+07', '2015-04-30 14:39:59+07', 'oHcViFPEq5sNN3rATjrusmLlQ3jzM5MEkqgsqfq7cYzB0SZ261dJQH61sH7p', NULL, '2015-04-30 14:39:59+07');
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login) VALUES (2, 'rina', '$2y$10$xMPQDRbEVwF3KwCRsVjs1.xUF55yRyYky5mzWNOPSL6wilwvV7m9S', 'Finance', '2015-04-28 19:56:50.457+07', '2015-04-28 19:56:50.457+07', NULL, NULL, NULL);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login) VALUES (13, 'sdfdf', '$2y$10$OeJ7DeDCKeEtOp96c8ZB1.l28r1CynXmVzEupt43lmt.cdzoZNyYW', 'Admin', '2015-05-01 04:00:41+07', '2015-05-01 04:00:41+07', NULL, NULL, NULL);


--
-- TOC entry 2070 (class 0 OID 33187)
-- Dependencies: 181
-- Data for Name: validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (5, 'Pengawan scaffolding', 3, '2015-05-01 10:02:46+07', '2015-05-02 13:10:27+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (2, 'SPK', 1, '2015-05-01 10:01:44+07', '2015-05-02 13:46:15+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (4, 'Marketing List', 4, '2015-05-01 10:02:33+07', '2015-05-02 14:06:57+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (7, 'dfgfdg', 3434, '2015-05-02 14:30:11+07', '2015-05-02 14:30:11+07', false);


--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 180
-- Name: validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('validasi_rules_id_seq', 7, true);


--
-- TOC entry 1933 (class 2606 OID 24979)
-- Name: pk4ree535; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk4ree535 PRIMARY KEY (id);


--
-- TOC entry 1941 (class 2606 OID 25014)
-- Name: pkdgdget; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling
    ADD CONSTRAINT pkdgdget PRIMARY KEY (id);


--
-- TOC entry 1945 (class 2606 OID 33206)
-- Name: pkdsfdwr3r34; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT pkdsfdwr3r34 PRIMARY KEY (id);


--
-- TOC entry 1937 (class 2606 OID 24992)
-- Name: pkdtewt; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai
    ADD CONSTRAINT pkdtewt PRIMARY KEY (id);


--
-- TOC entry 1939 (class 2606 OID 25003)
-- Name: pkfdgtet; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT pkfdgtet PRIMARY KEY (id);


--
-- TOC entry 1949 (class 2606 OID 41387)
-- Name: pkfdt464te; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling_validasi_rules
    ADD CONSTRAINT pkfdt464te PRIMARY KEY (id);


--
-- TOC entry 1943 (class 2606 OID 33195)
-- Name: pkrgte64354; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules
    ADD CONSTRAINT pkrgte64354 PRIMARY KEY (id);


--
-- TOC entry 1935 (class 2606 OID 24981)
-- Name: undfge3453; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT undfge3453 UNIQUE (name);


--
-- TOC entry 1947 (class 2606 OID 33208)
-- Name: undsferre; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT undsferre UNIQUE (kode);


--
-- TOC entry 1950 (class 2606 OID 33209)
-- Name: fk456rege54; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT fk456rege54 FOREIGN KEY (customer) REFERENCES customers(id);


--
-- TOC entry 1951 (class 2606 OID 33214)
-- Name: fkcvbgertet; Type: FK CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT fkcvbgertet FOREIGN KEY (exportir) REFERENCES customers(id);


-- Completed on 2015-05-04 22:07:12

--
-- PostgreSQL database dump complete
--

