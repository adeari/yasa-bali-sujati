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