TRUNCATE users restart identity;
TRUNCATE customers restart identity;
TRUNCATE filling restart identity;
TRUNCATE filling_validasi_rules restart identity;
TRUNCATE joborder restart identity;
TRUNCATE joborder_pegawai restart identity;
TRUNCATE joborder_validasi_rules restart identity;
TRUNCATE pegawai restart identity;
TRUNCATE validasi_rules restart identity;

INSERT INTO users (name, password, divisi, created_at, updated_at, remember_token, password_seen, last_login, isdeleted) VALUES ('ade', '$2a$08$cJk7IIoQ7Bal6GCR8Bmpee1uPFkmrB4teExWG4SDV0G8rfbMDcK/i', 'Admin', '2015-05-10 15:43:09.615+07', '2015-05-24 09:29:22+07', null, '1234', now(), false);