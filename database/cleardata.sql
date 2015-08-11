TRUNCATE users;
TRUNCATE customers;
TRUNCATE filling;
TRUNCATE filling_validasi_rules;
TRUNCATE joborder;
TRUNCATE joborder_pegawai;
TRUNCATE joborder_validasi_rules;
TRUNCATE pegawai;
TRUNCATE validasi_rules;

INSERT INTO users (name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES ('ade', '$2a$08$cJk7IIoQ7Bal6GCR8Bmpee1uPFkmrB4teExWG4SDV0G8rfbMDcK/i', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-24 09:29:22+07', null, '1234', now(), false);