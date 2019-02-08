insert into customer (id, username, password, active)
    values (1, 'admin', '123', true);

insert into customer_role (customer_id, roles)
    values (1, 'USER'), (1, 'ADMIN');