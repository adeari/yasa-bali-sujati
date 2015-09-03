ALTER TABLE pegawai ADD COLUMN detail character varying;
update  pegawai set  detail = (select (alamat ||  E'\n' || telepon ));
ALTER TABLE pegawai DROP COLUMN alamat;
ALTER TABLE pegawai DROP COLUMN telepon;

ALTER TABLE customers ADD COLUMN detail character varying;
update  customers set  detail = (select (contact_person ||  E'\n' || alamat ||  E'\n' || telepon ||  E'\n' || email ));
ALTER TABLE customers DROP COLUMN contact_person;
ALTER TABLE customers DROP COLUMN alamat;
ALTER TABLE customers DROP COLUMN telepon;
ALTER TABLE customers DROP COLUMN email;
ALTER TABLE customers DROP COLUMN islengkap;


ALTER TABLE joborder ADD COLUMN customername character varying;
ALTER TABLE joborder ADD COLUMN pegawainame character varying;

ALTER TABLE joborder_pegawai ADD COLUMN id bigserial NOT NULL;
ALTER TABLE ONLY joborder_pegawai ADD CONSTRAINT pkfdg454ee PRIMARY KEY (id);

ALTER TABLE joborder_validasi_rules ADD COLUMN id bigserial NOT NULL;
ALTER TABLE ONLY joborder_validasi_rules ADD CONSTRAINT pkfdg4464d PRIMARY KEY (id);

ALTER TABLE joborder ADD COLUMN consignee character varying;
ALTER TABLE joborder ADD COLUMN vessel character varying;
ALTER TABLE joborder ADD COLUMN blno character varying;
ALTER TABLE joborder ADD COLUMN containerno character varying;
ALTER TABLE joborder ADD COLUMN sealno character varying;
ALTER TABLE joborder ADD COLUMN type_of_wood_packing character varying;
ALTER TABLE joborder ADD COLUMN quantity character varying;
ALTER TABLE joborder ADD COLUMN treatment character varying;
ALTER TABLE joborder ADD COLUMN wood_core_temperatur character varying;
ALTER TABLE joborder ADD COLUMN exposure_time character varying;
ALTER TABLE joborder ADD COLUMN fumigant character varying;
ALTER TABLE joborder ADD COLUMN dosage_rate character varying;
ALTER TABLE joborder ADD COLUMN certificate_number character varying;
ALTER TABLE joborder ADD COLUMN tgl_cetak Date;
ALTER TABLE joborder ADD COLUMN downloadpath character varying;
ALTER TABLE joborder ADD COLUMN downloadpath_party character varying;