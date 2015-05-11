DELETE FROM users WHERE id > 1;
ALTER SEQUENCE users_id_seq RESTART WITH 2;

TRUNCATE customers;
TRUNCATE filling;
TRUNCATE filling_validasi_rules;
TRUNCATE joborder;
TRUNCATE joborder_pegawai;
TRUNCATE joborder_validasi_rules;
TRUNCATE pegawai;
TRUNCATE validasi_rules;
