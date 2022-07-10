CREATE SEQUENCE users_id_sequence minvalue 1 maxvalue 9999999999999;

CREATE TABLE users
(
    id          bigint PRIMARY KEY NOT NULL DEFAULT nextval('users_id_sequence'),
    user_name  VARCHAR(225),
    password    VARCHAR(225),
    email       VARCHAR(225),
    role        VARCHAR(225),
    reset_token VARCHAR(225),
    force_reset BOOLEAN NOT NULL DEFAULT false,
    status      VARCHAR(225)
);

CREATE SEQUENCE pizzas_id_sequence minvalue 1 maxvalue 9999999999999;

CREATE TABLE pizza(
    id          bigint PRIMARY KEY NOT NULL DEFAULT nextval('pizzas_id_sequence'),
    type        VARCHAR(225),
    name        VARCHAR(225),
    description VARCHAR(1000),
    price       numeric
);

CREATE SEQUENCE orders_id_sequence minvalue 1 maxvalue 9999999999999;

CREATE TABLE orders
(
    id      bigint PRIMARY KEY NOT NULL DEFAULT nextval('orders_id_sequence'),
    user_id bigint NOT NULL,
    status  VARCHAR(225),
    total   numeric,
    FOREIGN KEY (user_id) REFERENCES users(id)
);


CREATE SEQUENCE customer_orders_id_sequence minvalue 1 maxvalue 9999999999999;

CREATE TABLE customer_order
(
    id       bigint PRIMARY KEY NOT NULL DEFAULT nextval('customer_orders_id_sequence'),
    order_id bigint NOT NULL,
    pizza_id bigint NOT NULL,
    count integer NOT NULL,

    FOREIGN KEY (order_id) REFERENCES orders(id),
    FOREIGN KEY (pizza_id) REFERENCES pizza(id)
);
