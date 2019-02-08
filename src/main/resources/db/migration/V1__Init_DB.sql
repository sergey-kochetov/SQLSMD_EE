create sequence hibernate_sequence start 1 increment 1;

create table message (
    id int8 not null,
    filename varchar(255),
    tag varchar(255),
    text varchar(2048) not null,
    customer_id int8,
    primary key (id)
);

create table customer_role (
    customer_id int8 not null,
    roles varchar(255)
);

create table customer (
    id int8 not null,
    activation_code varchar(255),
    active boolean not null,
    email varchar(255),
    password varchar(255) not null,
    username varchar(255) not null,
    primary key (id)
);

alter table if exists message
    add constraint message_customer_fk
    foreign key (customer_id) references customer;

alter table if exists user_role
    add constraint user_role_customer_fk
    foreign key (customer_id) references customer;