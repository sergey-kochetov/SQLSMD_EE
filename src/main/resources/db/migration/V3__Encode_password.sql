CREATE extension IF NOT EXISTS pgcrypto;

UPDATE customer SET password = crypt(password, gen_salt('bf', 8));
