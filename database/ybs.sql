--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-09-14 22:35:49

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

DROP DATABASE dbybs;
--
-- TOC entry 2124 (class 1262 OID 24960)
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
-- TOC entry 2125 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON SCHEMA public IS 'standard public schema';


--
-- TOC entry 193 (class 3079 OID 11855)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2126 (class 0 OID 0)
-- Dependencies: 193
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_with_oids = false;

--
-- TOC entry 192 (class 1259 OID 66690)
-- Name: certificatenewcolumn; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE certificatenewcolumn (
    id bigint NOT NULL,
    joborder_id bigint,
    column_name character varying,
    description character varying
);


--
-- TOC entry 191 (class 1259 OID 66688)
-- Name: certificatenewcolumn_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE certificatenewcolumn_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2127 (class 0 OID 0)
-- Dependencies: 191
-- Name: certificatenewcolumn_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE certificatenewcolumn_id_seq OWNED BY certificatenewcolumn.id;


--
-- TOC entry 175 (class 1259 OID 24995)
-- Name: customers; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE customers (
    id integer NOT NULL,
    nama_perusahaan character varying,
    jenis_customer character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true,
    detail character varying
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
-- TOC entry 2128 (class 0 OID 0)
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
-- TOC entry 2129 (class 0 OID 0)
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
    destinasi character varying,
    pegawainame character varying,
    validasiname character varying,
    consignee character varying,
    vessel character varying,
    blno character varying,
    containerno character varying,
    type_of_wood_packing character varying,
    quantity character varying,
    treatment character varying,
    wood_core_temperatur character varying,
    exposure_time character varying,
    fumigant character varying,
    dosage_rate character varying,
    certificate_number character varying,
    tgl_cetak date,
    downloadpath character varying,
    downloadpath_party character varying,
    consigmentfile bytea,
    partyfile bytea
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
-- TOC entry 2130 (class 0 OID 0)
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
    pegawai_id integer,
    id bigint NOT NULL
);


--
-- TOC entry 189 (class 1259 OID 50265)
-- Name: joborder_pegawai_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE joborder_pegawai_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2131 (class 0 OID 0)
-- Dependencies: 189
-- Name: joborder_pegawai_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE joborder_pegawai_id_seq OWNED BY joborder_pegawai.id;


--
-- TOC entry 184 (class 1259 OID 49599)
-- Name: joborder_validasi_rules; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE joborder_validasi_rules (
    joborder_id bigint,
    validasi_rules_id integer,
    id bigint NOT NULL
);


--
-- TOC entry 190 (class 1259 OID 50273)
-- Name: joborder_validasi_rules_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE joborder_validasi_rules_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2132 (class 0 OID 0)
-- Dependencies: 190
-- Name: joborder_validasi_rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE joborder_validasi_rules_id_seq OWNED BY joborder_validasi_rules.id;


--
-- TOC entry 173 (class 1259 OID 24984)
-- Name: pegawai; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE pegawai (
    id integer NOT NULL,
    nama character varying,
    divisi character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    isdeleted boolean DEFAULT true,
    detail character varying
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
-- TOC entry 2133 (class 0 OID 0)
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
-- TOC entry 2134 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- TOC entry 188 (class 1259 OID 50245)
-- Name: users_java; Type: TABLE; Schema: public; Owner: -
--

CREATE TABLE users_java (
    id integer NOT NULL,
    name character varying,
    password character varying,
    divisi character varying,
    created_at timestamp with time zone,
    updated_at timestamp with time zone,
    password_seen character varying,
    last_login timestamp with time zone,
    isdeleted boolean DEFAULT true
);


--
-- TOC entry 187 (class 1259 OID 50243)
-- Name: users_java_id_seq; Type: SEQUENCE; Schema: public; Owner: -
--

CREATE SEQUENCE users_java_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


--
-- TOC entry 2135 (class 0 OID 0)
-- Dependencies: 187
-- Name: users_java_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE users_java_id_seq OWNED BY users_java.id;


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
-- TOC entry 2136 (class 0 OID 0)
-- Dependencies: 178
-- Name: validasi_rules_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: -
--

ALTER SEQUENCE validasi_rules_id_seq OWNED BY validasi_rules.id;


--
-- TOC entry 1963 (class 2604 OID 66693)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY certificatenewcolumn ALTER COLUMN id SET DEFAULT nextval('certificatenewcolumn_id_seq'::regclass);


--
-- TOC entry 1951 (class 2604 OID 50048)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- TOC entry 1953 (class 2604 OID 50049)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling ALTER COLUMN id SET DEFAULT nextval('filling_id_seq'::regclass);


--
-- TOC entry 1956 (class 2604 OID 50050)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder ALTER COLUMN id SET DEFAULT nextval('joborder_id_seq'::regclass);


--
-- TOC entry 1957 (class 2604 OID 50267)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder_pegawai ALTER COLUMN id SET DEFAULT nextval('joborder_pegawai_id_seq'::regclass);


--
-- TOC entry 1958 (class 2604 OID 50275)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder_validasi_rules ALTER COLUMN id SET DEFAULT nextval('joborder_validasi_rules_id_seq'::regclass);


--
-- TOC entry 1949 (class 2604 OID 50051)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai ALTER COLUMN id SET DEFAULT nextval('pegawai_id_seq'::regclass);


--
-- TOC entry 1960 (class 2604 OID 50052)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1961 (class 2604 OID 50248)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_java ALTER COLUMN id SET DEFAULT nextval('users_java_id_seq'::regclass);


--
-- TOC entry 1955 (class 2604 OID 50053)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules ALTER COLUMN id SET DEFAULT nextval('validasi_rules_id_seq'::regclass);


--
-- TOC entry 2119 (class 0 OID 66690)
-- Dependencies: 192
-- Data for Name: certificatenewcolumn; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2137 (class 0 OID 0)
-- Dependencies: 191
-- Name: certificatenewcolumn_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('certificatenewcolumn_id_seq', 1, false);


--
-- TOC entry 2102 (class 0 OID 24995)
-- Dependencies: 175
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (1, 'customer1', 'Rekanan', '2015-09-02 07:03:54.99+07', '2015-09-02 07:03:54.99+07', false, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (5, 'kildan', 'Rekanan', '2015-09-02 19:13:34.18+07', '2015-09-02 19:13:34.18+07', false, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (3, 'CUSTOMER2', 'Rekanan', '2015-09-02 07:05:23.516+07', '2015-09-02 07:05:23.516+07', false, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (9, 'zirmi', 'Rekanan', '2015-09-02 19:17:05.253+07', '2015-09-02 19:17:05.253+07', false, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (10, 'zirma', 'Exportir', '2015-09-02 19:17:05.253+07', '2015-09-02 19:17:05.253+07', false, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (8, 'muldin', 'Exportir', '2015-09-02 19:15:58.52+07', '2015-09-02 19:15:58.52+07', true, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (7, 'muldan', 'Rekanan', '2015-09-02 19:15:50.532+07', '2015-09-02 19:15:50.532+07', true, NULL);
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (6, 'shipperan', 'Exportir', '2015-09-02 19:13:34.18+07', '2015-09-02 19:13:34.18+07', false, 'milhan
');
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (2, 'shipper1 mun', 'Exportir', '2015-09-02 07:03:54.99+07', '2015-09-04 01:35:29.097+07', false, 'jalan merani4444
muna kito4545
ada riaon kul
');
INSERT INTO customers (id, nama_perusahaan, jenis_customer, created_at, updated_at, isdeleted, detail) VALUES (4, 'shipper2', 'Exportir', '2015-09-02 07:05:23.516+07', '2015-09-02 07:05:23.516+07', false, 'dfdf
dfd
');


--
-- TOC entry 2138 (class 0 OID 0)
-- Dependencies: 174
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customers_id_seq', 10, true);


--
-- TOC entry 2104 (class 0 OID 25006)
-- Dependencies: 177
-- Data for Name: filling; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (2, 'Kuning', 'ku_93', 3, 'ku_930002', '2015-09-02 07:00:32.303+07', '2015-09-02 07:00:32.303+07', false);
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (1, 'Biru', 'BT_', 4, 'BT_0003', '2015-09-02 07:00:04.823+07', '2015-09-02 07:00:11.39+07', false);


--
-- TOC entry 2139 (class 0 OID 0)
-- Dependencies: 176
-- Name: filling_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('filling_id_seq', 2, true);


--
-- TOC entry 2109 (class 0 OID 41379)
-- Dependencies: 182
-- Data for Name: filling_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--



--
-- TOC entry 2108 (class 0 OID 33198)
-- Dependencies: 181
-- Data for Name: joborder; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi, pegawainame, validasiname, consignee, vessel, blno, containerno, type_of_wood_packing, quantity, treatment, wood_core_temperatur, exposure_time, fumigant, dosage_rate, certificate_number, tgl_cetak, downloadpath, downloadpath_party, consigmentfile, partyfile) VALUES (5, 'ku_930001', 5, 6, 'kegi tuan', NULL, 'ceteat', '2015-09-02 19:13:34.18+07', 1, '2015-09-04 02:34:24.547+07', 1, 2, 'Sebagian', 't4', 'komitu 2
SS
ADA', 'partai kitaa', 'destinasi 2', '', '', 'consignee', 'vesselll', 'nooo bl', 'containerrr', 'ttpeee', 'quantityyy', 'FUMIGATION', '', 'timess', 'fumigant', 'dosage', 'certttt', '2015-09-16', 'Indoretail.rar', 'C:\Users\ade\Documents\suratkerja.docx', NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi, pegawainame, validasiname, consignee, vessel, blno, containerno, type_of_wood_packing, quantity, treatment, wood_core_temperatur, exposure_time, fumigant, dosage_rate, certificate_number, tgl_cetak, downloadpath, downloadpath_party, consigmentfile, partyfile) VALUES (6, 'BT_0002', 3, 4, 'kegi tuon', '2015-11-12 10:12:00+07', 'catat ya', '2015-09-02 19:13:38.368+07', 1, '2015-09-09 06:36:49.126+07', 1, 1, 'Selesai', 'tempat', 'komi
dfdfds
f', 'partai
dsfds
f', 'destino', 'Limban, Beruga', 'Berdan', 'vdfdfd
fdf
dsf
ds
f', 'dsfdsf', '4545', 'cxvddee', 'dfgdsfdfdsfdsf', '334', 'FUMIGATION', '', 'expos', 'fumi', 'dosa', 'fdgerr33434', '2015-09-16', 'C:\Users\ade\Documents\bengkel.mdb', 'C:\Users\ade\Documents\mundur.rtf', NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi, pegawainame, validasiname, consignee, vessel, blno, containerno, type_of_wood_packing, quantity, treatment, wood_core_temperatur, exposure_time, fumigant, dosage_rate, certificate_number, tgl_cetak, downloadpath, downloadpath_party, consigmentfile, partyfile) VALUES (8, 'BT_0003', 1, 2, '', NULL, '', '2015-09-02 19:13:43.714+07', 1, '2015-09-02 19:13:43.714+07', 1, 1, 'Sebagian', '', '', '', '', '', '', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi, pegawainame, validasiname, consignee, vessel, blno, containerno, type_of_wood_packing, quantity, treatment, wood_core_temperatur, exposure_time, fumigant, dosage_rate, certificate_number, tgl_cetak, downloadpath, downloadpath_party, consigmentfile, partyfile) VALUES (4, 'BT_0001', 1, 2, 'kegiatan', '2015-09-09 19:12:31.478+07', 'catan
kita d
udlu', '2015-09-02 19:12:55.549+07', 1, '2015-09-09 06:31:24.214+07', 1, 1, 'Selesai', 'tempat
kita ada', 'komodita 555 nuim mul', 'partaiku 88 nuim', 'destinasi', 'Beruga', 'jika', 'consignee', 'vessel', 'blno', 'container', 'type of wood', '23333', 'HEAT TREATMENT (HT)', '45', 'timer expos', 'rtrtr', 'yyyyy', 'nommmm2323', '2015-09-23', 'mundur.rtf', 'gitignore_global.txt', NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi, pegawainame, validasiname, consignee, vessel, blno, containerno, type_of_wood_packing, quantity, treatment, wood_core_temperatur, exposure_time, fumigant, dosage_rate, certificate_number, tgl_cetak, downloadpath, downloadpath_party, consigmentfile, partyfile) VALUES (7, 'ku_930002', 9, 10, '', NULL, 'cataad dehh', '2015-09-02 19:13:41.303+07', 1, '2015-09-02 19:20:38.181+07', 1, 1, 'Sebagian', 'tempat kita', 'komino', 'prtai', '', 'Limban', 'jika', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL);


--
-- TOC entry 2140 (class 0 OID 0)
-- Dependencies: 180
-- Name: joborder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_id_seq', 8, true);


--
-- TOC entry 2110 (class 0 OID 49591)
-- Dependencies: 183
-- Data for Name: joborder_pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder_pegawai (joborder_id, pegawai_id, id) VALUES (4, 1, 3);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id, id) VALUES (7, 4, 5);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id, id) VALUES (6, 4, 6);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id, id) VALUES (6, 1, 7);


--
-- TOC entry 2141 (class 0 OID 0)
-- Dependencies: 189
-- Name: joborder_pegawai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_pegawai_id_seq', 7, true);


--
-- TOC entry 2111 (class 0 OID 49599)
-- Dependencies: 184
-- Data for Name: joborder_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id, id) VALUES (4, 2, 2);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id, id) VALUES (7, 2, 4);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id, id) VALUES (6, 3, 5);


--
-- TOC entry 2142 (class 0 OID 0)
-- Dependencies: 190
-- Name: joborder_validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_validasi_rules_id_seq', 5, true);


--
-- TOC entry 2100 (class 0 OID 24984)
-- Dependencies: 173
-- Data for Name: pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pegawai (id, nama, divisi, created_at, updated_at, isdeleted, detail) VALUES (2, 'Zildan', 'Karyawan', '2015-09-02 07:02:00.882+07', '2015-09-02 07:02:00.882+07', true, 'detail zildan');
INSERT INTO pegawai (id, nama, divisi, created_at, updated_at, isdeleted, detail) VALUES (3, 'Milona', 'Karyawan', '2015-09-02 19:06:24.032+07', '2015-09-02 19:06:24.032+07', true, 'dess ritu');
INSERT INTO pegawai (id, nama, divisi, created_at, updated_at, isdeleted, detail) VALUES (1, 'Beruga', 'Karyawan', '2015-09-02 07:01:43.297+07', '2015-09-02 07:01:43.297+07', false, 'jl beruga');
INSERT INTO pegawai (id, nama, divisi, created_at, updated_at, isdeleted, detail) VALUES (4, 'Limban', 'Sales', '2015-09-02 19:06:37.461+07', '2015-09-02 19:06:37.461+07', false, 'Destailni');


--
-- TOC entry 2143 (class 0 OID 0)
-- Dependencies: 172
-- Name: pegawai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pegawai_id_seq', 4, true);


--
-- TOC entry 2113 (class 0 OID 49628)
-- Dependencies: 186
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (1, 'ade', '$2a$08$cJk7IIoQ7Bal6GCR8Bmpee1uPFkmrB4teExWG4SDV0G8rfbMDcK/i', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-24 09:29:22+07', NULL, '1234', '2015-09-02 06:59:01.368+07', false);


--
-- TOC entry 2144 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_id_seq', 1, true);


--
-- TOC entry 2115 (class 0 OID 50245)
-- Dependencies: 188
-- Data for Name: users_java; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users_java (id, name, password, divisi, created_at, updated_at, password_seen, last_login, isdeleted) VALUES (2, 'dina', '$2a$08$oVjuftIBqAyKJz.YRqMesOG8q37DDmk0ce1MA71F4LdcYEwsR6vX.', 'Admin', '2015-09-02 06:59:32.446+07', '2015-09-02 06:59:32.446+07', '123456', '2015-09-02 06:59:40.605+07', true);
INSERT INTO users_java (id, name, password, divisi, created_at, updated_at, password_seen, last_login, isdeleted) VALUES (3, 'nina', '$2a$08$EZOlJI.XKUqB0YnnanZNVuupFUTBQYaV/iGLV0PrA4oaD1HErQryu', 'Operator', '2015-09-02 19:06:10.553+07', '2015-09-02 19:06:10.553+07', '123456', NULL, true);
INSERT INTO users_java (id, name, password, divisi, created_at, updated_at, password_seen, last_login, isdeleted) VALUES (1, 'ade', '$2a$08$rCwpy6gQRNV.FNaqOvCT..9uCGB80OkqrSpkom2c8U5IpeNnH81KW', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-24 09:29:22+07', '1234', '2015-09-14 22:28:40.292+07', false);


--
-- TOC entry 2145 (class 0 OID 0)
-- Dependencies: 187
-- Name: users_java_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_java_id_seq', 3, true);


--
-- TOC entry 2106 (class 0 OID 33187)
-- Dependencies: 179
-- Data for Name: validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (2, 'jika', 2, '2015-09-02 07:04:44.904+07', '2015-09-02 07:04:44.904+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (1, 'lengkap', 1, '2015-09-02 07:04:30.191+07', '2015-09-02 07:04:30.191+07', true);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (3, 'Berdan', 3, '2015-09-02 19:14:19.213+07', '2015-09-02 19:14:19.213+07', false);


--
-- TOC entry 2146 (class 0 OID 0)
-- Dependencies: 178
-- Name: validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('validasi_rules_id_seq', 3, true);


--
-- TOC entry 1981 (class 2606 OID 49637)
-- Name: pk4ree535; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT pk4ree535 PRIMARY KEY (id);


--
-- TOC entry 1985 (class 2606 OID 50254)
-- Name: pk8979; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_java
    ADD CONSTRAINT pk8979 PRIMARY KEY (id);


--
-- TOC entry 1969 (class 2606 OID 25014)
-- Name: pkdgdget; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling
    ADD CONSTRAINT pkdgdget PRIMARY KEY (id);


--
-- TOC entry 1973 (class 2606 OID 33206)
-- Name: pkdsfdwr3r34; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT pkdsfdwr3r34 PRIMARY KEY (id);


--
-- TOC entry 1965 (class 2606 OID 24992)
-- Name: pkdtewt; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai
    ADD CONSTRAINT pkdtewt PRIMARY KEY (id);


--
-- TOC entry 1989 (class 2606 OID 66698)
-- Name: pkefe435435; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY certificatenewcolumn
    ADD CONSTRAINT pkefe435435 PRIMARY KEY (id);


--
-- TOC entry 1979 (class 2606 OID 50280)
-- Name: pkfdg4464d; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder_validasi_rules
    ADD CONSTRAINT pkfdg4464d PRIMARY KEY (id);


--
-- TOC entry 1977 (class 2606 OID 50272)
-- Name: pkfdg454ee; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder_pegawai
    ADD CONSTRAINT pkfdg454ee PRIMARY KEY (id);


--
-- TOC entry 1967 (class 2606 OID 25003)
-- Name: pkfdgtet; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers
    ADD CONSTRAINT pkfdgtet PRIMARY KEY (id);


--
-- TOC entry 1971 (class 2606 OID 33195)
-- Name: pkrgte64354; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules
    ADD CONSTRAINT pkrgte64354 PRIMARY KEY (id);


--
-- TOC entry 1983 (class 2606 OID 49639)
-- Name: undfge3453; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users
    ADD CONSTRAINT undfge3453 UNIQUE (name);


--
-- TOC entry 1975 (class 2606 OID 33208)
-- Name: undsferre; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder
    ADD CONSTRAINT undsferre UNIQUE (kode);


--
-- TOC entry 1987 (class 2606 OID 50256)
-- Name: uniu876; Type: CONSTRAINT; Schema: public; Owner: -
--

ALTER TABLE ONLY users_java
    ADD CONSTRAINT uniu876 UNIQUE (name);


-- Completed on 2015-09-14 22:35:52

--
-- PostgreSQL database dump complete
--

