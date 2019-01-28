DROP TABLE IF EXISTS customer;

CREATE TABLE customer
(
    c_id integer not null,
    c_name varchar(300),
    c_password varchar(300),
    PRIMARY KEY (c_id)
);

DROP TABLE IF EXISTS user_actions;

CREATE SEQUENCE user_actions_id_seq;
CREATE TABLE user_actions
(
    id integer NOT NULL DEFAULT nextval('user_actions_id_seq'),
    user_name varchar(300),
    db_name varchar(300),
    action varchar(300),
    PRIMARY KEY (id)
);

ALTER SEQUENCE user_actions_id_seq
OWNED BY user_actions.id;