--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-08-11 21:45:48

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE dbybs;
--
-- TOC entry 2086 (class 1262 OID 24960)
-- Name: dbybs; Type: DATABASE; Schema: -; Owner: -
--

CREATE DATABASE dbybs WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Indonesian_Indonesia.1252' LC_CTYPE = 'Indonesian_Indonesia.1252';


\connect dbybs

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: -
--

CREATE SCHEMA public;


--
-- TOC entry 2087 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 187 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2088 (class 0 OID 0)
-- Dependencies: 187
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 175 (class 1259 OID 24995)
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
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true,
    islengkap boolean DEFAULT false
);


--
-- TOC entry 174 (class 1259 OID 24993)
-- Name: customers_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE customers_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2089 (class 0 OID 0)
-- Dependencies: 174
-- Name: customers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE customers_id_seq OWNED BY customers.id;


--
-- TOC entry 177 (class 1259 OID 25006)
-- Name: filling; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE filling (
    id integer NOT NULL,
    warna character varying,
    huruf character varying,
    digit integer,
    nomor_terakhir character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true
);


--
-- TOC entry 176 (class 1259 OID 25004)
-- Name: filling_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE filling_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2090 (class 0 OID 0)
-- Dependencies: 176
-- Name: filling_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE filling_id_seq OWNED BY filling.id;


--
-- TOC entry 182 (class 1259 OID 41379)
-- Name: filling_validasi_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE filling_validasi_rules (
    validasi_rules_id integer,
    filling_id character varying
);


--
-- TOC entry 181 (class 1259 OID 33198)
-- Name: joborder; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE joborder (
    id bigint NOT NULL,
    kode character varying,
    customer integer,
    exportir integer,
    jenis_kegiatan character varying,
    tgl_pelaksanaan timestamp with time zone,
    catatan character varying,
    created_at timestamp with time zone,
    created_by integer,
    updated_at timestamp with time zone,
    updated_by integer,
    filling integer,
    status character varying,
    t4_pelaksanaan character varying,
    komoditi character varying,
    partai character varying,
    destinasi character varying
);


--
-- TOC entry 180 (class 1259 OID 33196)
-- Name: joborder_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE joborder_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2091 (class 0 OID 0)
-- Dependencies: 180
-- Name: joborder_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE joborder_id_seq OWNED BY joborder.id;


--
-- TOC entry 183 (class 1259 OID 49591)
-- Name: joborder_pegawai; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE joborder_pegawai (
    joborder_id bigint,
    pegawai_id integer
);


--
-- TOC entry 184 (class 1259 OID 49599)
-- Name: joborder_validasi_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE joborder_validasi_rules (
    joborder_id bigint,
    validasi_rules_id integer
);


--
-- TOC entry 173 (class 1259 OID 24984)
-- Name: pegawai; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pegawai (
    id integer NOT NULL,
    nama character varying,
    alamat character varying,
    telepon character varying,
    divisi character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true
);


--
-- TOC entry 172 (class 1259 OID 24982)
-- Name: pegawai_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE pegawai_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2092 (class 0 OID 0)
-- Dependencies: 172
-- Name: pegawai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE pegawai_id_seq OWNED BY pegawai.id;


--
-- TOC entry 186 (class 1259 OID 49628)
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
    last_login timestamp with time zone,
    isdeleted boolean DEFAULT true
);


--
-- TOC entry 185 (class 1259 OID 49626)
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2093 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 179 (class 1259 OID 33187)
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
-- TOC entry 178 (class 1259 OID 33185)
-- Name: validasi_rules_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE validasi_rules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2094 (class 0 OID 0)
-- Dependencies: 178
-- Name: validasi_rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE validasi_rules_id_seq OWNED BY validasi_rules.id;


--
-- TOC entry 1934 (class 2604 OID 50048)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- TOC entry 1936 (class 2604 OID 50049)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling ALTER COLUMN id SET DEFAULT nextval('filling_id_seq'::regclass);


--
-- TOC entry 1939 (class 2604 OID 50050)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder ALTER COLUMN id SET DEFAULT nextval('joborder_id_seq'::regclass);


--
-- TOC entry 1931 (class 2604 OID 50051)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai ALTER COLUMN id SET DEFAULT nextval('pegawai_id_seq'::regclass);


--
-- TOC entry 1941 (class 2604 OID 50052)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1938 (class 2604 OID 50053)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules ALTER COLUMN id SET DEFAULT nextval('validasi_rules_id_seq'::regclass);


--
-- TOC entry 2070 (class 0 OID 24995)
-- Dependencies: 175
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (34, 'Pt rikomin', 'pilhanm', 'alam rim', '56566', '', 'Rekanan', '2015-05-22 23:00:05+07', '2015-05-22 23:00:05+07', true, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (37, 'pt himm ranm', 'porinam', 'alam rikanm', '54545', '', 'teman', '2015-05-22 23:01:36+07', '2015-05-22 23:08:31+07', false, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (38, 'purrr', NULL, NULL, NULL, NULL, 'Importir', '2015-05-22 23:12:46+07', '2015-05-22 23:12:47+07', false, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (35, 'pt yuli', 'pildam', 'jl. polim', '45453', '', 'Exportir', '2015-05-22 23:00:28+07', '2015-05-22 23:17:56+07', false, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (36, 'pt ryuinim', 'pirdan', 'alammmm', '43545', '', 'Importir', '2015-05-22 23:00:45+07', '2015-05-22 23:19:27+07', false, true);


--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 174
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customers_id_seq', 38, true);


--
-- TOC entry 2072 (class 0 OID 25006)
-- Dependencies: 177
-- Data for Name: filling; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (18, 'riss', 'PIR_', 5, NULL, '2015-05-22 23:03:03+07', '2015-05-22 23:09:52+07', false);
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (17, 'bitu', 'KOL_', 5, NULL, '2015-05-22 23:02:04+07', '2015-05-22 23:12:47+07', false);


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 176
-- Name: filling_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('filling_id_seq', 18, true);


--
-- TOC entry 2077 (class 0 OID 41379)
-- Dependencies: 182
-- Data for Name: filling_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (14, '18');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (15, '18');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (14, '17');


--
-- TOC entry 2076 (class 0 OID 33198)
-- Dependencies: 181
-- Data for Name: joborder; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (56, 'PIR_00002', 37, NULL, 'kegiatan baru', '2015-11-11 12:12:00+07', '', '2015-05-22 23:09:52+07', 1, '2015-05-22 23:09:52+07', 1, 18, 'Kosong', '', 'komoditi', '20 Feet', 'desss');
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (57, 'KOL_00001', 38, NULL, 'kegiatan baru', '2001-10-10 11:21:00+07', '', '2015-05-22 23:12:47+07', 1, '2015-05-22 23:15:46+07', 1, 17, 'Kosong', '', 'latt', 'pil', 'dess');
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (55, 'PIR_00001', 37, 35, 'kegiatan baru', '2015-11-11 12:12:00+07', '', '2015-05-22 23:08:30+07', 1, '2015-05-22 23:17:54+07', 1, 18, 'Kosong', '', 'komoditi', '20 Feet', 'desss');
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (58, 'KOL_00002', 37, 36, 'bersamaku', '2015-12-12 12:12:00+07', 'catat dia dulu', '2015-05-22 23:18:39+07', 1, '2015-05-22 23:21:52+07', 1, 17, 'Kosong', 'ada dehh', 'rilo', '20 Feet', '');


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 180
-- Name: joborder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_id_seq', 58, true);


--
-- TOC entry 2078 (class 0 OID 49591)
-- Dependencies: 183
-- Data for Name: joborder_pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (58, 19);


--
-- TOC entry 2079 (class 0 OID 49599)
-- Dependencies: 184
-- Data for Name: joborder_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2068 (class 0 OID 24984)
-- Dependencies: 173
-- Data for Name: pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (19, 'Jaka1', 'jl. milham1', '945451', 'Kantor', '2015-05-22 22:59:04+07', '2015-05-22 23:21:52+07', false);


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 172
-- Name: pegawai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pegawai_id_seq', 19, true);


--
-- TOC entry 2081 (class 0 OID 49628)
-- Dependencies: 186
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (1, 'ade', '$2y$10$ahMMS0JRNSpgu1zIPJiwr.0F3RdWsZ.1ZtvDOq9pp1jlvGLvFSKT6', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-24 09:29:22+07', 'wFQ6Pbj0K811PpzEbySjYYRccFTBNr7N5o7AQAdJFWTpIMNTw5qLBvlFtntq', '1234', '2015-05-24 09:29:13+07', false);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (3, 'rima', '$2y$10$LzS9ef0/QrzS5EqBLFQKaO.5gx6w55PWEvR.9i5G4Oi4kl056tfyC', 'Admin', '2015-05-24 09:29:29+07', '2015-05-24 09:29:29+07', NULL, '123456', NULL, true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (4, 'lisa', '$2y$10$19qW3sDW4fbOlzbMdSfYG.7.JUruSLMqfRgDn.aTnDm9xEcPgZhu6', 'Finanace', '2015-05-24 09:29:42+07', '2015-05-24 09:29:58+07', NULL, '1234', NULL, true);


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_id_seq', 4, true);


--
-- TOC entry 2074 (class 0 OID 33187)
-- Dependencies: 179
-- Data for Name: validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (14, 'skc
', 1, '2015-05-22 23:02:19+07', '2015-05-22 23:03:05+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (15, 'KOKI', 2, '2015-05-22 23:02:31+07', '2015-05-22 23:03:05+07', false);


--
-- TOC entry 2100 (class 0 OID 0)
-- Dependencies: 178
-- Name: validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('validasi_rules_id_seq', 15, true);


--
-- TOC entry 1955 (class 2606 OID 49637)
-- Name: pk4ree535; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk4ree535 PRIMARY KEY (id);


--
-- TOC entry 1947 (class 2606 OID 25014)
-- Name: pkdgdget; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling
    ADD CONSTRAINT pkdgdget PRIMARY KEY (id);


--
-- TOC entry 1951 (class 2606 OID 33206)
-- Name: pkdsfdwr3r34; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT pkdsfdwr3r34 PRIMARY KEY (id);


--
-- TOC entry 1943 (class 2606 OID 24992)
-- Name: pkdtewt; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai
    ADD CONSTRAINT pkdtewt PRIMARY KEY (id);


--
-- TOC entry 1945 (class 2606 OID 25003)
-- Name: pkfdgtet; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT pkfdgtet PRIMARY KEY (id);


--
-- TOC entry 1949 (class 2606 OID 33195)
-- Name: pkrgte64354; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules
    ADD CONSTRAINT pkrgte64354 PRIMARY KEY (id);


--
-- TOC entry 1957 (class 2606 OID 49639)
-- Name: undfge3453; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT undfge3453 UNIQUE (name);


--
-- TOC entry 1953 (class 2606 OID 33208)
-- Name: undsferre; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT undsferre UNIQUE (kode);


-- Completed on 2015-08-11 21:45:49

--
-- PostgreSQL database dump complete
--

