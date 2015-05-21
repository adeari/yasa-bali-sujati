--
-- PostgreSQL database dump
--

-- Dumped from database version 9.4.1
-- Dumped by pg_dump version 9.4.1
-- Started on 2015-05-21 23:01:04

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

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
-- TOC entry 1932 (class 2604 OID 24998)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY customers ALTER COLUMN id SET DEFAULT nextval('customers_id_seq'::regclass);


--
-- TOC entry 1935 (class 2604 OID 25009)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY filling ALTER COLUMN id SET DEFAULT nextval('filling_id_seq'::regclass);


--
-- TOC entry 1939 (class 2604 OID 33201)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY joborder ALTER COLUMN id SET DEFAULT nextval('joborder_id_seq'::regclass);


--
-- TOC entry 1930 (class 2604 OID 24987)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY pegawai ALTER COLUMN id SET DEFAULT nextval('pegawai_id_seq'::regclass);


--
-- TOC entry 1940 (class 2604 OID 49631)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- TOC entry 1937 (class 2604 OID 33190)
-- Name: id; Type: DEFAULT; Schema: public; Owner: -
--

ALTER TABLE ONLY validasi_rules ALTER COLUMN id SET DEFAULT nextval('validasi_rules_id_seq'::regclass);


--
-- TOC entry 2070 (class 0 OID 24995)
-- Dependencies: 175
-- Data for Name: customers; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (19, 'CV. Mirhan', 'Kalinim', 'Jl. Rizzz konim', '0845442', 'raihan@yooni.net', 'Rekanan', '2015-05-10 16:18:58+07', '2015-05-10 16:18:58+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (17, 'pt. wijaya', 'milham', 'Jl. Pol dim', '4354356', 'Milhanim@yom.com', 'Exportir', '2015-05-10 16:17:59+07', '2015-05-10 19:23:24+07', false, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (20, 'PT. TE LTD', 'Rika san', 'Jl. Liss nuim', '56453433', 'milrum@nuni.com', 'Rekanan', '2015-05-10 16:20:11+07', '2015-05-10 19:59:18+07', false, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (15, 'pt. Zuika555', 'winda', 'Jl. HAlnam', '4354356', 'ade@yo.net', 'Rekanan', '2015-05-10 16:15:44+07', '2015-05-10 16:15:44+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (21, 'PT. rika nanda', 'rika', 'jl. polian', '065643', 'mildanim@yoi.net', 'TEman', '2015-05-21 06:35:32+07', '2015-05-21 06:35:32+07', true, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (22, 'PT. Lingkar timur', 'Sandri', 'jl. pattimura', '56454', 'riska@yoo.net', 'Saudara', '2015-05-21 06:36:08+07', '2015-05-21 06:36:23+07', true, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (23, 'Pt. randika', 'randi', 'alam dia', '045445', '', 'Importir', '2015-05-21 19:07:19+07', '2015-05-21 19:07:19+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (24, 'PT liano rin', 'polin', 'alamt  dion
', '445456', '', 'Exportir', '2015-05-21 19:24:09+07', '2015-05-21 19:29:22+07', true, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (16, 'PT. dwika candra', 'rinda', 'jl. mislanim', '67566', '', 'Importir', '2015-05-10 16:17:30+07', '2015-05-21 19:29:37+07', false, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (18, 'CV. Mubinan1', 'windma1', 'jl. rinaman', '675661', '', 'Exportir', '2015-05-10 16:18:22+07', '2015-05-21 19:30:07+07', false, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (25, 'PT. lin danim', 'prorita', 'alam duim', '454523', '', 'Importir', '2015-05-21 19:27:02+07', '2015-05-21 19:38:39+07', true, true);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (26, 'Pt. adalainn', NULL, NULL, NULL, NULL, 'TEman', '2015-05-21 22:01:52+07', '2015-05-21 22:01:52+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (27, 'pt. shipper lain', NULL, NULL, NULL, NULL, 'Importir', '2015-05-21 22:01:53+07', '2015-05-21 22:01:53+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (28, 'sdsd', NULL, NULL, NULL, NULL, 'Rekanan', '2015-05-21 22:02:49+07', '2015-05-21 22:02:49+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (29, 'sdssf', NULL, NULL, NULL, NULL, 'Exportir', '2015-05-21 22:02:51+07', '2015-05-21 22:02:51+07', true, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (30, 'sdsd', NULL, NULL, NULL, NULL, 'Importir', '2015-05-21 22:37:55+07', '2015-05-21 22:37:56+07', false, false);
INSERT INTO customers (id, nama_perusahaan, contact_person, alamat, telepon, email, jenis_customer, created_at, updated_at, isdeleted, islengkap) VALUES (31, 'sdsds', NULL, NULL, NULL, NULL, 'Importir', '2015-05-21 22:37:56+07', '2015-05-21 22:37:56+07', false, false);


--
-- TOC entry 2095 (class 0 OID 0)
-- Dependencies: 174
-- Name: customers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('customers_id_seq', 31, true);


--
-- TOC entry 2072 (class 0 OID 25006)
-- Dependencies: 177
-- Data for Name: filling; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (15, 'Kuning', 'POI', 3, NULL, '2015-05-10 16:54:38+07', '2015-05-10 19:23:24+07', false);
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (10, 'Hijau', 'HIB', 5, NULL, '2015-05-10 16:22:46+07', '2015-05-19 19:05:52+07', false);
INSERT INTO filling (id, warna, huruf, digit, nomor_terakhir, created_at, updated_at, isdeleted) VALUES (16, 'Jingga', 'PO_', 4, NULL, '2015-05-20 20:06:26+07', '2015-05-20 20:11:33+07', false);


--
-- TOC entry 2096 (class 0 OID 0)
-- Dependencies: 176
-- Name: filling_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('filling_id_seq', 16, true);


--
-- TOC entry 2077 (class 0 OID 41379)
-- Dependencies: 182
-- Data for Name: filling_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (8, '15');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (9, '15');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (12, '16');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (8, '16');
INSERT INTO filling_validasi_rules (validasi_rules_id, filling_id) VALUES (11, '16');


--
-- TOC entry 2076 (class 0 OID 33198)
-- Dependencies: 181
-- Data for Name: joborder; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (48, 'POI002', 20, 16, 'Bersama kamu', '2012-12-12 10:12:00+07', 'catat ajah dia', '2015-05-10 19:59:18+07', 1, '2015-05-10 19:59:18+07', 1, 15, 'Kosong', NULL, NULL, NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (49, 'HIB00001', 20, 16, 'Bersama kamu', '1010-10-10 21:11:00+06:42:04', 'bcb', '2015-05-19 19:05:52+07', 1, '2015-05-19 19:05:52+07', 1, 10, 'Kosong', NULL, NULL, NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (50, 'PO_0001', 18, 18, 'Bersama kamu', '2011-11-12 12:12:00+07', 'CATATAN', '2015-05-20 20:11:32+07', 1, '2015-05-20 20:11:32+07', 1, 16, 'Kosong', NULL, NULL, NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (47, 'POI001', 16, 17, 'ayoo giat belajar', '2015-12-10 12:21:00+07', 'Catat hal ini', '2015-05-10 19:23:23+07', 1, '2015-05-20 20:29:51+07', 1, 15, 'Lengkap', NULL, NULL, NULL, NULL);
INSERT INTO joborder (id, kode, customer, exportir, jenis_kegiatan, tgl_pelaksanaan, catatan, created_at, created_by, updated_at, updated_by, filling, status, t4_pelaksanaan, komoditi, partai, destinasi) VALUES (53, 'PO_0002', 30, 31, 'sdsds', '2015-12-12 11:11:00+07', 'ccssd', '2015-05-21 22:37:56+07', 1, '2015-05-21 22:59:35+07', 1, 16, 'Sebagian', 'xcxzcxzsf', 'zxcxzc', '40 Feet', 'cc');


--
-- TOC entry 2097 (class 0 OID 0)
-- Dependencies: 180
-- Name: joborder_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('joborder_id_seq', 53, true);


--
-- TOC entry 2078 (class 0 OID 49591)
-- Dependencies: 183
-- Data for Name: joborder_pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (47, 12);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (48, 15);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (48, 12);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (50, 12);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (50, 15);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (50, 18);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (50, 11);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (50, 17);
INSERT INTO joborder_pegawai (joborder_id, pegawai_id) VALUES (53, 15);


--
-- TOC entry 2079 (class 0 OID 49599)
-- Dependencies: 184
-- Data for Name: joborder_validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (48, 12);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (48, 11);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (47, 8);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (47, 11);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (47, 9);
INSERT INTO joborder_validasi_rules (joborder_id, validasi_rules_id) VALUES (53, 12);


--
-- TOC entry 2068 (class 0 OID 24984)
-- Dependencies: 173
-- Data for Name: pegawai; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (16, 'Pordis', 'Jl. Poldanam', '0343455', 'Finance', '2015-05-10 16:20:32+07', '2015-05-10 19:21:17+07', true);
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (12, 'Raia', 'jl. Kuningam', '43545', 'Administrasi', '2015-05-10 15:57:48+07', '2015-05-10 19:23:24+07', false);
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (15, 'Filda', 'Jl. pildan', '4354356', 'Finance', '2015-05-10 16:06:14+07', '2015-05-10 19:59:18+07', false);
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (18, 'laila', 'jl. ponidi', '043545', 'Operation', '2015-05-20 20:03:07+07', '2015-05-20 20:11:33+07', false);
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (11, 'Zolda', 'jl. prima', '435456', 'Manager', '2015-05-10 15:57:22+07', '2015-05-20 20:11:33+07', false);
INSERT INTO pegawai (id, nama, alamat, telepon, divisi, created_at, updated_at, isdeleted) VALUES (17, 'Laras', 'Jl. Rukin ham', '454563', 'Finance', '2015-05-10 16:20:52+07', '2015-05-20 20:11:33+07', false);


--
-- TOC entry 2098 (class 0 OID 0)
-- Dependencies: 172
-- Name: pegawai_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('pegawai_id_seq', 18, true);


--
-- TOC entry 2081 (class 0 OID 49628)
-- Dependencies: 186
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (4, 'sila', '$2y$10$tkED3BExhEuV/V9SmobwLeoZZbOnKKjm2ywwRRR7GTaSUOXcsJnRS', 'Marketing', '2015-05-21 04:41:32+07', '2015-05-21 04:41:32+07', NULL, NULL, NULL, true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (2, 'rina', '$2y$10$5yfRIJHb11aUT.VgZlDiNOWC.4/YwzYlf2aU6NactM/lsvvMuf1U.', 'Finance', '2015-05-10 15:56:18+07', '2015-05-10 15:59:28+07', 'O29NmJQFDdI0kPJuw1LvYXIXMR7bYvY1QNmPpiPcWFD7C0fHlxXTds4gncht', NULL, '2015-05-10 15:59:24+07', true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (5, 'rena', '$2y$10$mKhqn5lBTcGUoTZobY.1UO4czPc8i6lFaeO6NusJ.23zHIjE3L32q', 'Marketing', '2015-05-21 04:44:08+07', '2015-05-21 04:44:08+07', NULL, NULL, NULL, true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (6, 'linasa', '$2y$10$yudqlf6VQqzBgL3imSm10eJYQvecWOlffIxdxmErY.41Z5ZdcDH8K', 'Marketing', '2015-05-21 04:47:17+07', '2015-05-21 05:05:44+07', 'I29TPo6hxQdWkWOlcMVrGuUsPJHbuqO0eBX23oWvdjFWfNaEpHQzNCSLSZos', '12347', '2015-05-21 05:05:38+07', true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (3, 'dila', '$2y$10$CZ2T0TPqullY9AyW6vQNauBU1dOgWxBq3zrhzVxUpYkPj2Y0WWLhK', 'Marketing', '2015-05-20 19:58:01+07', '2015-05-21 06:04:06+07', 'hSIn6HNX0meDKIbVx8iu79bVuYipFGW8o9jCy2TiIlQwTCFvjIbAtt3GcZYY', '11111', '2015-05-20 19:58:36+07', true);
INSERT INTO users (id, name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES (1, 'ade', '$2y$10$xMPQDRbEVwF3KwCRsVjs1.xUF55yRyYky5mzWNOPSL6wilwvV7m9S', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-21 19:10:56+07', 'IjB2QXWdj6F87yFYN3iT5Ngh1mTd0UytRI8HYZYc4zAD4c23rxw3OFadlH3z', NULL, '2015-05-21 19:10:56+07', false);


--
-- TOC entry 2099 (class 0 OID 0)
-- Dependencies: 185
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('users_id_seq', 6, true);


--
-- TOC entry 2074 (class 0 OID 33187)
-- Dependencies: 179
-- Data for Name: validasi_rules; Type: TABLE DATA; Schema: public; Owner: -
--

INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (12, 'SPK', 1, '2015-05-10 16:58:12+07', '2015-05-10 19:59:18+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (11, 'CEk urutan penomoran kayu', 3, '2015-05-10 16:29:04+07', '2015-05-10 19:59:18+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (13, 'cEK LIST', 2, '2015-05-20 20:06:54+07', '2015-05-20 20:06:54+07', true);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (8, 'Pengawan scaffolding', 2, '2015-05-10 16:24:19+07', '2015-05-10 19:22:18+07', false);
INSERT INTO validasi_rules (id, aturan, urutan, created_at, updated_at, isdeleted) VALUES (9, 'Marketing List', 4, '2015-05-10 16:24:32+07', '2015-05-10 19:22:18+07', false);


--
-- TOC entry 2100 (class 0 OID 0)
-- Dependencies: 178
-- Name: validasi_rules_id_seq; Type: SEQUENCE SET; Schema: public; Owner: -
--

SELECT pg_catalog.setval('validasi_rules_id_seq', 13, true);


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


-- Completed on 2015-05-21 23:01:07

--
-- PostgreSQL database dump complete
--

